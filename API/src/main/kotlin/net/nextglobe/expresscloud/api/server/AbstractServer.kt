package net.nextglobe.expresscloud.api.server

import net.nextglobe.expresscloud.api.category.Category
import java.util.UUID

abstract class AbstractServer(override val id: UUID = UUID.randomUUID(), override val category: Category, override val number: Int = 1,
                              override var state: ServerState = ServerState.CREATED) : CategoryServer, StatefulServer {

    override val name: String
        get() = category.name + "-" + number

}