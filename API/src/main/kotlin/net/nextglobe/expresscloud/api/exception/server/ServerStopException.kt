package net.nextglobe.expresscloud.api.exception.server

import net.nextglobe.expresscloud.api.exception.CloudException

class ServerStopException : CloudException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}