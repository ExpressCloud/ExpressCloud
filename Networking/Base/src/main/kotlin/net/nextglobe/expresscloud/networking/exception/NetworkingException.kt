package net.nextglobe.expresscloud.networking.exception

import net.nextglobe.expresscloud.api.exception.CloudException

class NetworkingException : CloudException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}