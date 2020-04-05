package net.nextglobe.expresscloud.db.exception

import net.nextglobe.expresscloud.api.exception.CloudException

open class ActiveDatabaseCloudConfigsException : CloudException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}