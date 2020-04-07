package net.nextglobe.expresscloud.implementation.docker

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.async.ResultCallback
import com.github.dockerjava.api.command.DockerCmdExecFactory
import com.github.dockerjava.api.model.Event
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.netty.NettyDockerCmdExecFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import net.nextglobe.expresscloud.api.category.Category
import net.nextglobe.expresscloud.api.server.CategorizedServer
import net.nextglobe.expresscloud.api.server.CategoryServer
import net.nextglobe.expresscloud.api.server.Server
import net.nextglobe.expresscloud.cm.CloudManager
import net.nextglobe.expresscloud.implementation.docker.exception.NoImageInformationException
import net.nextglobe.expresscloud.implementation.docker.utils.ImageUtils
import net.nextglobe.expresscloud.implementation.docker.utils.TarUtils
import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

val logger = KotlinLogging.logger {}

private const val NETWORK_NAME: String = "expresscloud-docker-network"
private const val IMAGE_PREFIX: String = "expresscloud-docker-"
private const val BASE_IMAGE_PREFIX: String = IMAGE_PREFIX + "base-"
private const val CONTAINER_PREFIX: String = "expresscloud-"

class DockerCloudManager : CloudManager {

    private lateinit var dockerClient: DockerClient

    override val implementationName: String = "Docker"
    override val needsConnection: Boolean = true

    override fun connect(connectionString: String) {
        logger.info { "Connecting to \"$connectionString\" without TLS..." }

        val dockerClientConfig: DockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
            .withDockerHost(connectionString)
//            .withDockerTlsVerify(true)
//            .withDockerCertPath("/home/user/.docker")
            .build()

        val dockerCmdExecFactory: DockerCmdExecFactory = NettyDockerCmdExecFactory().withConnectTimeout(10000)

        dockerClient = DockerClientImpl.getInstance(dockerClientConfig).withDockerCmdExecFactory(dockerCmdExecFactory)

        if(dockerClient.listNetworksCmd().withNameFilter(NETWORK_NAME).exec().count() == 0) {
            logger.info { "Creating custom network ($NETWORK_NAME)..." }
            val id = dockerClient.createNetworkCmd().withName(NETWORK_NAME).withOptions(mapOf(Pair("subnet", "1.0.0.0/24"))).exec().id
            logger.debug { "Created network with id $id" }
        }
        logger.info { "Stopping & deleting all ExpressCloud containers..." }
        runBlocking {
            dockerClient.listContainersCmd().withNetworkFilter(listOf(NETWORK_NAME)).exec().forEach {
                launch {
                    dockerClient.stopContainerCmd(it.id).exec()
                    dockerClient.removeContainerCmd(it.id).exec()
                    logger.debug { "Stopped & deleted container ${it.names[0]} (${it.id})" }
                }
            }
        }
        logger.info { "Initialization finished!" }
    }

    override fun registerListeners() {
        dockerClient.eventsCmd().also { it.filters?.set("network", listOf(NETWORK_NAME)) }.exec(object : ResultCallback.Adapter<Event>() {
            override fun onNext(event: Event) {
                logger.debug { "ID: ${event.id} Action: ${event.action}" }
            }
        }).awaitStarted()
    }

    override fun disconnect() {
        dockerClient.close()
    }

    private fun prepareBaseImage(javaVersionTag: String): Boolean {
        val imageName = BASE_IMAGE_PREFIX + "openjdk-" + javaVersionTag
        logger.debug { "Checking if base image ($imageName) exists..." }
        if(dockerClient.listImagesCmd().withImageNameFilter(imageName).exec().isEmpty()) { // Base image does not exist
            logger.info { "Base image ($imageName) does not exist! Creating..." }
            javaClass.getResourceAsStream("/BaseImage").use { dockerfileInputStream ->
                ByteArrayOutputStream().use { outputStream ->
                    TarArchiveOutputStream(outputStream).use {
                        it.putArchiveEntry(TarArchiveEntry("Dockerfile"))
                        it.write(dockerfileInputStream.readAllBytes())
                        it.closeArchiveEntry()
                        it.finish()
                    }
                    val response = dockerClient.createImageCmd(imageName, ByteArrayInputStream(outputStream.toByteArray())).exec()
                    logger.info { "Base image created (${response.id})!" }
                }
            }
        } else {
            logger.debug { "Base image does already exist!" }
        }
        return true
    }

    private fun prepareImageForCategory(category: Category, rebuild: Boolean = false): Boolean {
        val imageName = IMAGE_PREFIX + category.name
        if(dockerClient.listImagesCmd().withImageNameFilter(imageName).exec().isEmpty() || rebuild) { // Image does not exist or rebuild was explicitly turned on
            logger.info { "Image $imageName does not exist! Creating..." }
            ByteArrayOutputStream().use { outputStream ->
                logger.debug { "Copying resources into image..." }
                val serverJarFile = File(category.paths.serverJarPath)
                if(serverJarFile.exists() && serverJarFile.isFile) {
                    TarArchiveOutputStream(outputStream).use {
                        it.putArchiveEntry(TarArchiveEntry("Dockerfile"))
                        // TODO read base image from category
                        // TODO add ram parameters to start command
                        // TODO add support for custom parameters to start command
                        it.write(ImageUtils.createServerImage(BASE_IMAGE_PREFIX + "openjdk-11-slim", "java -jar server.jar").toByteArray())
                        it.closeArchiveEntry()
                        it.putArchiveEntry(it.createArchiveEntry(serverJarFile, "server.jar"))
                        it.closeArchiveEntry()
                        TarUtils.addDirectoryToTar(category.paths.worldsFolderPath, "worlds", it)
                        TarUtils.addDirectoryToTar(category.paths.pluginsFolderPath, "plugins", it)
                        TarUtils.addDirectoryToTar(category.paths.configsFolderPath, "configs", it)
                        it.finish()
                    }
                    val response = dockerClient.createImageCmd(imageName, ByteArrayInputStream(outputStream.toByteArray())).exec()
                    logger.info { "Image created (${response.id})!" }
                } else {
                    logger.error { "The server jar file does not exist! Can't create image [${category.paths.serverJarPath}]" }
                    return false
                }
            }
        } else {
            logger.debug { "Image does already exist!" }
        }
        return true
    }

    private suspend fun prepareImageWithoutPreparingBase(server: Server): Boolean {
        logger.debug { "Preparing image for ${server.name}..." }
        if(server is CategorizedServer) {
            return prepareImageForCategory(server.category)
        } else {
            throw NoImageInformationException()
        }
    }

    override suspend fun prepareImage(server: Server): Boolean {
        prepareBaseImage("11-slim") // TODO read from category
        return prepareImageWithoutPreparingBase(server)
    }

    override suspend fun prepareImages(servers: List<Server>): Boolean = coroutineScope {
        prepareBaseImage("11-slim") // TODO read from category
        servers.distinctBy { (it as CategorizedServer).category }.map { async { prepareImageWithoutPreparingBase(it) } }.all { it.await() }
    }

    // TODO add rebuildImage(s)

    private fun createContainer(imageName: String, name: String): String {
        return dockerClient.createContainerCmd(imageName).withName(name).exec().id
    }

    private fun createContainerForCategory(category: Category, name: String): String {
        return createContainer(IMAGE_PREFIX + category.name, name)
    }

    override suspend fun createServer(server: Server): String {
        prepareImage(server)
        // No typecheck needed as prepareImage already throws an exception if server is not of type CategorizedServer
        return createContainerForCategory((server as CategorizedServer).category, CONTAINER_PREFIX + server.category.name.toLowerCase() + "-" + (if(server is CategoryServer) server.number else server.id))
    }

    override suspend fun createServers(servers: List<Server>): List<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun startServer(server: Server): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun startServers(servers: List<Server>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun stopServer(server: Server): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun stopServerById(serverId: UUID): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun stopServers(servers: List<Server>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun stopServersById(serverIds: List<UUID>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteServer(server: Server): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteServerById(serverId: UUID): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteServers(servers: List<Server>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteServersById(serverIds: List<UUID>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}