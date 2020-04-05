package net.nextglobe.expresscloud.base

import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import net.nextglobe.expresscloud.api.exception.CloudException
import net.nextglobe.expresscloud.api.exception.cloudmanager.CloudManagerException
import net.nextglobe.expresscloud.base.manager.ServerManager
import net.nextglobe.expresscloud.base.startup.Startup
import net.nextglobe.expresscloud.cm.CloudManager
import net.nextglobe.expresscloud.db.Database
import net.nextglobe.expresscloud.db.models.DatabaseCloudConfig

val logger = KotlinLogging.logger {}

object Cloud {

    private var _cloudManager: CloudManager? = null
    private var _serverManager: ServerManager? = null
    private var _database: Database? = null

    var cloudManager : CloudManager
        get() {
            return _cloudManager ?: throw CloudManagerException("The cloud manager hasn't been initialized yet")
        }
        private set(value) {
            _cloudManager = value
        }

    var serverManager : ServerManager
        get() {
            return _serverManager ?: throw CloudException("The server manager hasn't been initialized yet")
        }
        private set(value) {
            _serverManager = value
        }

    var database : Database
        get() {
            return _database ?: throw CloudException("The database hasn't been initialized yet")
        }
        private set(value) {
            _database = value
        }

    var state = CloudState.WAITING_FOR_START

    fun start() = runBlocking {
        if(state == CloudState.WAITING_FOR_START) {
            val config = Startup.loadConfig()
            val database = Startup.initializeDatabase(config)
            val databaseCloudConfig = DatabaseCloudConfig.getActiveDatabaseCloudConfigStrict(database.getDatabaseCloudConfigs())
            cloudManager = Startup.initializeCloudManager(databaseCloudConfig)
            state = CloudState.STARTED
        } else {
            throw CloudException("Cloud has already been started")
        }
    }

    fun stop() {
        if(state == CloudState.STARTED) {
            cloudManager.disconnect()
        } else {
            throw CloudException("The cloud hasn't been started or hasn't completed starting yet")
        }
    }

}