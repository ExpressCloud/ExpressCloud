package net.nextglobe.expresscloud.networking.message

interface SerializableNetworkMessage<out T> : NetworkMessage {

    fun serialize() : T

}