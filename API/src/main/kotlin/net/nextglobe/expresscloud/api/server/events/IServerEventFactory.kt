package net.nextglobe.expresscloud.api.server.events

import net.nextglobe.expresscloud.api.server.Server

interface IServerEventFactory {

    fun newServerCreatedEvent(server: Server) : ServerCreatedEvent

}