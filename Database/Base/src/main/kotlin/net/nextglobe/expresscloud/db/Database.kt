package net.nextglobe.expresscloud.db

import net.nextglobe.expresscloud.db.models.DatabaseCategory
import net.nextglobe.expresscloud.db.models.DatabaseCloudConfig
import java.util.UUID

interface Database {

    fun connect(connectionString: String, database: String)

    fun buildConnectionString(username: String?, password: String?, hostname: String?, port: Int?, authenticationDatabase: String?) : String

    suspend fun getDatabaseCloudConfigs(): List<DatabaseCloudConfig>

    suspend fun getDatabaseCloudConfig(uuid: UUID): DatabaseCloudConfig?

    suspend fun insertDatabaseCloudConfig(cloudConfig: DatabaseCloudConfig)

    suspend fun updateDatabaseCloudConfig(cloudConfig: DatabaseCloudConfig)

    suspend fun getDatabaseCategories(): List<DatabaseCategory>

    suspend fun getDatabaseCategory(uuid: UUID): DatabaseCategory?

    suspend fun insertDatabaseCategory(category: DatabaseCategory)

    suspend fun updateDatabaseCategory(category: DatabaseCategory)

}