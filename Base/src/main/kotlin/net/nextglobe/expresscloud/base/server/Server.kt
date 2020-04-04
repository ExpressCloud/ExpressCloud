package net.nextglobe.expresscloud.base.server

import net.nextglobe.expresscloud.api.category.Category
import net.nextglobe.expresscloud.api.server.AbstractServer
import net.nextglobe.expresscloud.api.server.ServerState
import net.nextglobe.expresscloud.base.Cloud
import java.util.UUID

class Server(id: UUID = UUID.randomUUID(), category: Category, number: Int = 1, state: ServerState = ServerState.CREATED) : AbstractServer(id, category, number, state) {

    override suspend fun start(): Boolean {
        return Cloud.serverManager.start(this)
    }

    override suspend fun stop(): Boolean {
        return Cloud.serverManager.stop(this)
    }

}