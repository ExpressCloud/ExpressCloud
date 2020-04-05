package net.nextglobe.expresscloud.cm

import net.nextglobe.expresscloud.api.server.Server
import java.util.UUID

interface CloudManager {

    val implementationName: String
    val needsConnection: Boolean

    /**
     * Optionally connect to the management solution (for example Docker). This function is called
     * if the particular CloudManager implementation has been selected by the user (in the configuration)
     */
    fun connect(connectionString: String)

    /**
     * Optionally register listeneres here. This function is called
     * if the particular CloudManager implementation has been selected by the user (in the configuration)
     */
    fun registerListeners()

    /**
     * Optionally disconnect from the management solution (for example Docker). This function is called
     * if the particular CloudManager implementation has been selected by the user (in the configuration)
     */
    fun disconnect()

    /**
     * Used to prepare an image. For example, the docker implementation would
     * create a new image (if it does not exist). A wrapper implementation would copy the needed files
     * into a directory on all available machines
     * @return true if the preparation was successful
     */
    suspend fun prepareImage(server: Server) : Boolean

    /**
     * See [prepareImage]
     */
    suspend fun prepareImages(servers: List<Server>) : Boolean


    /**
     * Used to create a server. For example, the docker implementation would
     * first call [prepareImage] and then a container would be created
     * @return true if the creation was successful
     */
    suspend fun createServer(server: Server) : Boolean

    /**
     * See [createServer]
     */
    suspend fun createServers(servers: List<Server>) : Boolean


    /**
     * Used to start a server. For example, the docker implementation would
     * first call [createServer] and then the created container would started
     * @return true if the start was successful
     */
    suspend fun startServer(server: Server) : Boolean

    /**
     * See [startServer]
     */
    suspend fun startServers(servers: List<Server>) : Boolean


    /**
     * Used to stop a started server. For example, the docker implementation would
     * first stop the created container and then [deleteServer] would be called
     * @return true if the stop was successful
     */
    suspend fun stopServer(server: Server) : Boolean

    /**
     * See [stopServer]
     */
    suspend fun stopServers(servers: List<Server>) : Boolean

    /**
     * See [stopServer]
     */
    suspend fun stopServerById(serverId: UUID) : Boolean

    /**
     * See [stopServer]
     */
    suspend fun stopServersById(serverIds: List<UUID>) : Boolean


    /**
     * Used to delete a created server. For example the docker implementation would
     * delete the created container
     * @return true if the deletion was successful
     */
    suspend fun deleteServer(server: Server) : Boolean

    /**
     * See [deleteServer]
     */
    suspend fun deleteServers(servers: List<Server>) : Boolean

    /**
     * See [deleteServer]
     */
    suspend fun deleteServerById(serverId: UUID) : Boolean

    /**
     * See [deleteServer]
     */
    suspend fun deleteServersById(serverIds: List<UUID>) : Boolean

}