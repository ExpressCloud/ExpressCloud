package net.nextglobe.expresscloud.networking.event

import net.nextglobe.expresscloud.events.SerializableCloudEvent
import net.nextglobe.expresscloud.networking.message.SerializableNetworkMessage

interface SerializableNetworkCloudEvent<T> : SerializableNetworkMessage<T>, SerializableCloudEvent<T>, NetworkCloudEvent