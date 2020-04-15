package net.nextglobe.expresscloud.api.events.server

import net.nextglobe.expresscloud.api.events.CloudEvent
import net.nextglobe.expresscloud.api.server.Server

interface ServerEvent : CloudEvent {

    val server: Server

}