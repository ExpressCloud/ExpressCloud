package net.nextglobe.expresscloud.networking.serialize.netty.events

import io.netty.buffer.ByteBuf
import net.nextglobe.expresscloud.api.server.Server
import net.nextglobe.expresscloud.api.server.events.ServerCreatedEvent
import net.nextglobe.expresscloud.networking.NetworkManager
import net.nextglobe.expresscloud.networking.serialize.netty.NettyUtils
import net.nextglobe.expresscloud.networking.serialize.netty.SerializeableNettyNetworkCloudEvent

class SerializeableServerCreatedEvent(server: Server) : SerializeableNettyNetworkCloudEvent, ServerCreatedEvent(server) {

    override fun serialize(): ByteBuf {
        return NettyUtils.newByteBuf().also {
            NettyUtils.writeUUID(it, server.id)
            NettyUtils.writeString(it, server.name)
        }
    }

    override fun dispatch() {
        NetworkManager.dispatch(this)
        super.dispatch()
    }

}