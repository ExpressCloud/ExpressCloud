package net.nextglobe.expresscloud.networking.serialize.netty

import io.netty.buffer.ByteBuf
import net.nextglobe.expresscloud.networking.serialize.SerializableNetworkMessage

interface SerializeableNettyNetworkMessage : SerializableNetworkMessage<ByteBuf>