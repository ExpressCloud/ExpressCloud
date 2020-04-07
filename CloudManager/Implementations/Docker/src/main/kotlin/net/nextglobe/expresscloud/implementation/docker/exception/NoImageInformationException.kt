package net.nextglobe.expresscloud.implementation.docker.exception

import net.nextglobe.expresscloud.api.exception.cloudmanager.CloudManagerException

class NoImageInformationException : CloudManagerException {
    constructor(message: String = "The provided server has no image information") : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}