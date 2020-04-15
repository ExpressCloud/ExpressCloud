package net.nextglobe.expresscloud.networking.netty.events

import net.nextglobe.expresscloud.api.events.server.IServerEventFactory
import net.nextglobe.expresscloud.api.events.server.ServerCreatedEvent
import net.nextglobe.expresscloud.api.events.server.ServerStartedEvent
import net.nextglobe.expresscloud.api.events.server.ServerStartingEvent
import net.nextglobe.expresscloud.api.server.Server
import net.nextglobe.expresscloud.networking.netty.serialize.events.SerializableServerCreatedEvent

object ServerEventFactory : IServerEventFactory {

    override fun createServerCreatedEvent(server: Server): ServerCreatedEvent {
        return SerializableServerCreatedEvent(server)
    }

    override fun createServerStartedEvent(server: Server): ServerStartedEvent {
        TODO("Not yet implemented")
    }

    override fun createServerStartingEvent(server: Server): ServerStartingEvent {
        TODO("Not yet implemented")
    }

}