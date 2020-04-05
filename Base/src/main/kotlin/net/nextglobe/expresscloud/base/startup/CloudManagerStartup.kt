package net.nextglobe.expresscloud.base.startup

import mu.KotlinLogging
import net.nextglobe.expresscloud.api.exception.cloudmanager.CloudManagerException
import net.nextglobe.expresscloud.cm.CloudManager
import net.nextglobe.expresscloud.db.models.DatabaseCloudConfig
import net.nextglobe.expresscloud.implementation.docker.DockerCloudManager

object CloudManagerStartup {

    private val logger = KotlinLogging.logger {}

    fun initializeCloudManager(cloudConfig: DatabaseCloudConfig): CloudManager {
        val implementation = cloudConfig.cloudManagerImplementation.toUpperCase()
        logger.info { "Selected cloud manager implementation: $implementation. Initializing..." }
        val cloudManager: CloudManager
        if (implementation == "DOCKER") {
            cloudManager = DockerCloudManager()
        } else {
            throw CloudManagerException("Cloud manager implementation does not exist") // TODO replace with fallback?
        }
        logger.info { "Initialized cloud manager implementation!" }
        return cloudManager
    }

    fun connectToCloudManager(cloudManager: CloudManager, cloudConfig: DatabaseCloudConfig) {
        logger.info { "Connecting to cloud manager..." }
        try {
            cloudManager.connect(cloudConfig.cloudManagerConnectionString ?: throw CloudManagerException("The cloud manager connection string was null. The selected implementation needs a connection and therefore a connection string"))
        } catch (e: Exception) {
            throw CloudManagerException("An error occurred during connecting to the cloud manager!", e)
        }
        logger.info { "Connected to cloud manager!" }
    }

    fun registerCloudManagerListeners(cloudManager: CloudManager) {
        logger.info { "Registering cloud manager listeners..." }
        try {
            cloudManager.registerListeners()
        } catch (e: Exception) {
            throw CloudManagerException("An error occurred during registering the cloud manager listeners!", e)
        }
        logger.info { "Cloud manager listeners registered!" }
    }

}