package net.nextglobe.expresscloud.networking.message

import net.nextglobe.expresscloud.networking.NetworkManager

interface NetworkMessage {

    fun dispatch() {
        NetworkManager.dispatch(this)
    }

}