package net.nextglobe.expresscloud.api.server

interface StatefulServer : Server {

    var state: ServerState

    override fun starting(): Boolean {
        return state == ServerState.STARTING
    }

    override fun started(): Boolean {
        return state == ServerState.STARTED
    }

    override fun stopping(): Boolean {
        return state == ServerState.STOPPING
    }

    override fun stopped(): Boolean {
        return state == ServerState.STOPPED
    }

    override fun deleted(): Boolean {
        return state == ServerState.STOPPED
    }

    fun created(): Boolean {
        return state == ServerState.CREATED
    }

}