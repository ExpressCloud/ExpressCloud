package net.nextglobe.expresscloud.networking.serialize

interface SerializableNetworkMessage<out T> {

    fun serialize() : T

}