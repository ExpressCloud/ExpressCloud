package net.nextglobe.expresscloud.base.server.events

import net.nextglobe.expresscloud.api.server.Server
import net.nextglobe.expresscloud.api.server.events.IServerEventFactory
import net.nextglobe.expresscloud.api.server.events.ServerCreatedEvent
import net.nextglobe.expresscloud.networking.serialize.netty.events.SerializeableServerCreatedEvent

class ServerEventFactory : IServerEventFactory {

    override fun newServerCreatedEvent(server: Server): ServerCreatedEvent {
        return SerializeableServerCreatedEvent(server)
    }

}