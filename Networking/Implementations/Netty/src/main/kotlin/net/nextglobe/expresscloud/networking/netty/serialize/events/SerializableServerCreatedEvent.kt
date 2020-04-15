package net.nextglobe.expresscloud.networking.netty.serialize.events

import io.netty.buffer.ByteBuf
import net.nextglobe.expresscloud.api.events.server.ServerCreatedEvent
import net.nextglobe.expresscloud.api.server.Server
import net.nextglobe.expresscloud.networking.netty.serialize.SerializableNettyNetworkCloudEvent
import net.nextglobe.expresscloud.networking.netty.utils.NettyUtils

class SerializableServerCreatedEvent(override val server: Server) : SerializableNettyNetworkCloudEvent, ServerCreatedEvent {

    override fun serialize(): ByteBuf {
        return NettyUtils.newByteBuf().also {
            NettyUtils.writeUUID(it, server.id)
            NettyUtils.writeString(it, server.name)
        }
    }

}