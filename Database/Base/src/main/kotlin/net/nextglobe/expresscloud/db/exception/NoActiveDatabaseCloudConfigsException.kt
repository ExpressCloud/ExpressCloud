package net.nextglobe.expresscloud.db.exception

class NoActiveDatabaseCloudConfigsException : ActiveDatabaseCloudConfigsException {
    constructor(message: String = "No database cloud config is active. Exactly one config must be active at a time") : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}