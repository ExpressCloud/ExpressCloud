package net.nextglobe.expresscloud.base.manager

import net.nextglobe.expresscloud.api.exception.server.ServerStartException
import net.nextglobe.expresscloud.api.server.ServerState
import net.nextglobe.expresscloud.base.Cloud
import net.nextglobe.expresscloud.base.server.Server
import java.util.UUID

class ServerManager {

    private val servers : MutableList<Server> = mutableListOf()

    fun addServer(server: Server) : Boolean {
        if(servers.none { it.id == server.id })
            return servers.add(server)
        return false
    }

    fun removeServer(server: Server) : Boolean {
        return removeServer(server.id)
    }

    fun removeServer(serverId: UUID) : Boolean {
        return servers.removeIf {
            it.id == serverId
        }
    }

    fun getServer(serverId: UUID) : Server? {
        return servers.find { it.id == serverId }
    }

    suspend fun start(server: Server) : Boolean {
        if(server.starting()) throw ServerStartException("The server is already starting")
        if(server.started()) throw ServerStartException("The server already started")
        if(server.stopping()) throw ServerStartException("The server is currently stopping")
        return Cloud.cloudManager.startServer(server)
    }

    suspend fun startAll() {
        Cloud.cloudManager.startServers(servers.filter { !it.stopping() && !it.starting() && !it.started() })
    }

    suspend fun stop(server: Server): Boolean {
        if(server.stopping()) throw ServerStartException("The server is already stopping")
        if(server.stopped()) throw ServerStartException("The server already stopped")
        if(server.starting()) throw ServerStartException("The server is currently starting")
        server.state = ServerState.STOPPING
        if(Cloud.cloudManager.stopServer(server)) {
            server.state = ServerState.STOPPED
        } else {
            server.state = ServerState.STARTED
        }
        return server.stopped()
    }

    suspend fun stop(serverId: UUID): Boolean {
        return stop(getServer(serverId) ?: return false)
    }

    suspend fun stopAll() {
        Cloud.cloudManager.stopServers(servers.filter { !it.starting() && !it.stopping() && !it.stopped() })
    }

}