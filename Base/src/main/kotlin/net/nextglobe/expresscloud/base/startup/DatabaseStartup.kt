package net.nextglobe.expresscloud.base.startup

import mu.KotlinLogging
import net.nextglobe.expresscloud.api.exception.CloudException
import net.nextglobe.expresscloud.base.config.DatabaseConnectionConfig
import net.nextglobe.expresscloud.db.Database
import net.nextglobe.expresscloud.db.implementation.mongodb.MongoDatabase

object DatabaseStartup {

    private val logger = KotlinLogging.logger {}

    fun initializeDatabase(implementation: String): Database {
        logger.info { "Selected database implementation: $implementation. Initializing..." }
        when (implementation) {
            "MongoDB" -> {
                return MongoDatabase()
            }
            else -> throw CloudException("Database implementation does not exist")
        }
    }

    fun connectToDatabase(database: Database, connectionConfig: DatabaseConnectionConfig) {
        logger.info { "Initialized database implementation! Connecting..." }
        try {
            database.connect(connectionConfig.connectionString ?: database.buildConnectionString(
                connectionConfig.username,
                connectionConfig.password,
                connectionConfig.hostname,
                connectionConfig.port,
                connectionConfig.authenticationDatabase
            ), connectionConfig.database ?: "expresscloud")
        } catch (e: Exception) {
            throw CloudException("Could not connect to database!", e)
        }
    }

}