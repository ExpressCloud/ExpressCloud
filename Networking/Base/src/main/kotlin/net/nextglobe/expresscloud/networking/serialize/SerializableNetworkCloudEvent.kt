package net.nextglobe.expresscloud.networking.serialize

import net.nextglobe.expresscloud.events.SerializableCloudEvent

interface SerializableNetworkCloudEvent<T> : SerializableNetworkMessage<T>, SerializableCloudEvent<T>