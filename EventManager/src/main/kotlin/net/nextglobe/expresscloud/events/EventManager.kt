package net.nextglobe.expresscloud.events

import com.google.common.eventbus.EventBus

object EventManager {

    @Suppress("UnstableApiUsage")
    private val bus : EventBus = EventBus()

    /**
     * Register an [eventHandler] object. Use [com.google.common.eventbus.Subscribe] to subscribe to a specific event
     * or a supertype to receive multiple event types
     */
    fun register(eventHandler: Any) {
        bus.register(eventHandler)
    }

    internal fun dispatchEvent(event: Any) {
        bus.post(event)
    }

}