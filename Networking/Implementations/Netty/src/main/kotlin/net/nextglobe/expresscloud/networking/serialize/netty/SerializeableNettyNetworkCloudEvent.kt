package net.nextglobe.expresscloud.networking.serialize.netty

import io.netty.buffer.ByteBuf
import net.nextglobe.expresscloud.networking.serialize.SerializableNetworkCloudEvent

interface SerializeableNettyNetworkCloudEvent : SerializableNetworkCloudEvent<ByteBuf>