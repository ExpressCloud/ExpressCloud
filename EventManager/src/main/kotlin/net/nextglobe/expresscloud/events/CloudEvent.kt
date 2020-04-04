package net.nextglobe.expresscloud.events

interface CloudEvent {

    fun dispatch() {
        EventManager.dispatchEvent(this)
    }

}