package net.nextglobe.expresscloud.db

import net.nextglobe.expresscloud.db.models.DatabaseCategory
import net.nextglobe.expresscloud.db.models.DatabaseConfig
import java.util.UUID

interface Database {

    fun connect(connectionString: String, database: String)

    fun buildConnectionString(username: String?, password: String?, hostname: String?, port: Int?, authenticationDatabase: String?) : String

    suspend fun getDatabaseConfigs(): List<DatabaseConfig>

    suspend fun getDatabaseConfig(uuid: UUID): DatabaseConfig?

    suspend fun insertDatabaseConfig(config: DatabaseConfig)

    suspend fun updateDatabaseConfig(config: DatabaseConfig)

    suspend fun getDatabaseCategories(): List<DatabaseCategory>

    suspend fun getDatabaseCategory(uuid: UUID): DatabaseCategory?

    suspend fun insertDatabaseCategory(category: DatabaseCategory)

    suspend fun updateDatabaseCategory(category: DatabaseCategory)

}