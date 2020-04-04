package net.nextglobe.expresscloud.events

interface SerializableCloudEvent<T> : CloudEvent {

    fun serialize() : T

}