package net.nextglobe.expresscloud.networking

import net.nextglobe.expresscloud.networking.message.NetworkMessage

interface INetworkManager {

    fun connect(connectionString: String)

    fun registerListener()

    fun dispatch(message: NetworkMessage)

    fun disconnect()

}