package net.nextglobe.expresscloud.base.startup

import io.github.config4k.extract
import io.github.config4k.toConfig
import mu.KotlinLogging
import net.nextglobe.expresscloud.base.config.CloudConfig
import net.nextglobe.expresscloud.base.config.DatabaseConnectionConfig
import net.nextglobe.expresscloud.cm.CloudManager
import net.nextglobe.expresscloud.config.FileConfiguration
import net.nextglobe.expresscloud.db.Database
import net.nextglobe.expresscloud.db.models.DatabaseCloudConfig
import java.nio.file.Paths

object Startup {

    private val logger = KotlinLogging.logger {}

    fun loadConfig(): CloudConfig {
        logger.debug { "Loading configuration..." }
        val cloudConfig = FileConfiguration(Paths.get("settings.conf"))
        if (!cloudConfig.exists()) {
            logger.info { "Configuration does not exist, creating the default config..." }
            cloudConfig.save(
                CloudConfig(
                    "MongoDB",
                    DatabaseConnectionConfig("admin", "root", "expresscloud", "expresscloud", "localhost", 27017, null)
                ).toConfig("cloudConfig")
            )
        }
        return cloudConfig.load().extract("cloudConfig")
    }

    fun initializeDatabase(config: CloudConfig) : Database {
        val database = DatabaseStartup.initializeDatabase(config.databaseImplementation)
        DatabaseStartup.connectToDatabase(database, config.databaseConnectionConfiguration)
        return database
    }

    fun initializeCloudManager(cloudConfig: DatabaseCloudConfig) : CloudManager {
        val cloudManager = CloudManagerStartup.initializeCloudManager(cloudConfig)
        if(cloudManager.needsConnection)
            CloudManagerStartup.connectToCloudManager(cloudManager, cloudConfig)
        CloudManagerStartup.registerCloudManagerListeners(cloudManager)
        return cloudManager
    }

}