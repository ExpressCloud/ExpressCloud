package net.nextglobe.expresscloud.implementation.docker

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.command.DockerCmdExecFactory
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.netty.NettyDockerCmdExecFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import net.nextglobe.expresscloud.api.server.Server
import net.nextglobe.expresscloud.cm.CloudManager
import java.util.*

val logger = KotlinLogging.logger {}

class DockerCloudManager : CloudManager {

    private val NETWORK_NAME: String = "expresscloud-docker-network"
    private val IMAGE_PREFIX: String = "expresscloud-docker-"

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
//        dockerClient.eventsCmd().
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun disconnect() {
        dockerClient.close()
    }

    override suspend fun prepareImage(server: Server): Boolean {
//        if(server is CategorizedServer) {
//            dockerClient.createImageCmd(IMAGE_PREFIX + server.category.name, )
////            server.category.name
//        } else {
//            throw IllegalArgumentException("The provided server has no image information")
//        }
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun prepareImages(servers: List<Server>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun createServer(server: Server): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun createServers(servers: List<Server>): Boolean {
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