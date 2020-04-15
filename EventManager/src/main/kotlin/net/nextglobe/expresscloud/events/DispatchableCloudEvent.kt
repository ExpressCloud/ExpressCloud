package net.nextglobe.expresscloud.events

import net.nextglobe.expresscloud.api.events.CloudEvent

interface DispatchableCloudEvent : CloudEvent {

    fun dispatch() {
        EventManager.dispatchEvent(this)
    }

}