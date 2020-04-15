package net.nextglobe.expresscloud.networking

import mu.KotlinLogging
import net.nextglobe.expresscloud.networking.exception.NetworkingException
import net.nextglobe.expresscloud.networking.message.NetworkMessage

private val logger = KotlinLogging.logger {}

object NetworkManager {

    private var networkManager: INetworkManager? = null

    fun initialize(networkManager: INetworkManager) {
        this.networkManager = networkManager
        logger.debug { "Network manager initialized!" }
    }

    fun dispatch(message: NetworkMessage) {
        networkManager?.dispatch(message) ?: throw NetworkingException("The network manager hasn't been initialized yet!")
    }

}