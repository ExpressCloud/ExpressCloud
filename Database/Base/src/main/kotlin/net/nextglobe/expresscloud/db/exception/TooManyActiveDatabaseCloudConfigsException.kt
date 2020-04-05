package net.nextglobe.expresscloud.db.exception

class TooManyActiveDatabaseCloudConfigsException : ActiveDatabaseCloudConfigsException {
    constructor(message: String = "More than one database cloud configs are active. Only one config can be active at a time") : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}