package net.nextglobe.expresscloud.api.exception.cloudmanager

import net.nextglobe.expresscloud.api.exception.CloudException

open class CloudManagerException : CloudException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}