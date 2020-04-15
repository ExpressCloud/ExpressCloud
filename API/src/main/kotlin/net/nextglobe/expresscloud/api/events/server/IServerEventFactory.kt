package net.nextglobe.expresscloud.api.events.server

import net.nextglobe.expresscloud.api.server.Server

interface IServerEventFactory {

    fun createServerCreatedEvent(server: Server): ServerCreatedEvent

    fun createServerStartedEvent(server: Server): ServerStartedEvent

    fun createServerStartingEvent(server: Server): ServerStartingEvent

}