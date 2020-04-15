package net.nextglobe.expresscloud.networking.netty.serialize

import io.netty.buffer.ByteBuf
import net.nextglobe.expresscloud.networking.event.SerializableNetworkCloudEvent

interface SerializableNettyNetworkCloudEvent : SerializableNetworkCloudEvent<ByteBuf>