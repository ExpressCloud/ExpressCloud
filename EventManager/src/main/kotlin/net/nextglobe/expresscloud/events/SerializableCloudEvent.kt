package net.nextglobe.expresscloud.events

import net.nextglobe.expresscloud.api.events.CloudEvent

interface SerializableCloudEvent<T> : CloudEvent {

    fun serialize(): T

}