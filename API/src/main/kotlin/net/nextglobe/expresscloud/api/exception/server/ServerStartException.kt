package net.nextglobe.expresscloud.api.exception.server

import net.nextglobe.expresscloud.api.exception.CloudException

class ServerStartException : ServerException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}