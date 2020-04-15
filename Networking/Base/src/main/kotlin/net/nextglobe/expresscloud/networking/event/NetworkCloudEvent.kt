package net.nextglobe.expresscloud.networking.event

import net.nextglobe.expresscloud.events.DispatchableCloudEvent
import net.nextglobe.expresscloud.networking.message.NetworkMessage

interface NetworkCloudEvent : NetworkMessage, DispatchableCloudEvent {

    override fun dispatch() {
        super<NetworkMessage>.dispatch()
        super<DispatchableCloudEvent>.dispatch()
    }

}