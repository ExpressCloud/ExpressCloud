package net.nextglobe.expresscloud.networking.netty.serialize

import io.netty.buffer.ByteBuf
import net.nextglobe.expresscloud.networking.message.SerializableNetworkMessage

interface SerializableNettyNetworkMessage :
    SerializableNetworkMessage<ByteBuf>