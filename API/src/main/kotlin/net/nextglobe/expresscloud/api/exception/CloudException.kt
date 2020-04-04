package net.nextglobe.expresscloud.api.exception

import java.lang.RuntimeException

open class CloudException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}