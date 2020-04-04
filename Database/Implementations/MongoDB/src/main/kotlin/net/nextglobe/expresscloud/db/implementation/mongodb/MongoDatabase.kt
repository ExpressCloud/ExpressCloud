package net.nextglobe.expresscloud.db.implementation.mongodb

import com.google.common.net.PercentEscaper
import net.nextglobe.expresscloud.db.Database
import net.nextglobe.expresscloud.db.models.DatabaseCategory
import net.nextglobe.expresscloud.db.models.DatabaseConfig
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import java.util.UUID

private val escaper = PercentEscaper("-", false)

class MongoDatabase : Database {

    private lateinit var client: CoroutineClient
    private lateinit var database: CoroutineDatabase

    private lateinit var databaseConfigsCollection: CoroutineCollection<DatabaseConfig>
    private lateinit var databaseCategoriesCollection: CoroutineCollection<DatabaseCategory>

    override fun connect(connectionString: String, database: String) {
        this.client = KMongo.createClient(connectionString).coroutine
        this.database = client.getDatabase(database)

        this.databaseConfigsCollection = this.database.getCollection("databaseConfigs")
        this.databaseCategoriesCollection = this.database.getCollection("databaseCategories")
    }

    override fun buildConnectionString(
        username: String?,
        password: String?,
        hostname: String?,
        port: Int?,
        authenticationDatabase: String?
    ) = buildString {
        append("mongodb://")

        // User
        if(!username.isNullOrBlank()) {
            append(username)
            if(!password.isNullOrBlank()) {
                append(":${escaper.escape(password)}")
            }
            append("@")
        }

        // Host
        append(if (hostname.isNullOrBlank()) "localhost" else hostname)

        // Port
        append(":${port ?: 27017}")

        // Authentication database
        if(!authenticationDatabase.isNullOrBlank())
            append("/$authenticationDatabase")
    }

    override suspend fun getDatabaseConfigs(): List<DatabaseConfig> {
        return databaseConfigsCollection.find().toList()
    }

    override suspend fun getDatabaseConfig(uuid: UUID): DatabaseConfig? {
        return databaseConfigsCollection.find(DatabaseConfig::uuid eq uuid).first()
    }

    override suspend fun insertDatabaseConfig(config: DatabaseConfig) {
        databaseConfigsCollection.insertOne(config)
    }

    override suspend fun updateDatabaseConfig(config: DatabaseConfig) {
        databaseConfigsCollection.updateOne(DatabaseConfig::uuid eq config.uuid, config)
    }

    override suspend fun getDatabaseCategories(): List<DatabaseCategory> {
        return databaseCategoriesCollection.find().toList()
    }

    override suspend fun getDatabaseCategory(uuid: UUID): DatabaseCategory? {
        return databaseCategoriesCollection.find(DatabaseCategory::uuid eq uuid).first()
    }

    override suspend fun insertDatabaseCategory(category: DatabaseCategory) {
        databaseCategoriesCollection.insertOne(category)
    }

    override suspend fun updateDatabaseCategory(category: DatabaseCategory) {
        databaseCategoriesCollection.updateOne(DatabaseConfig::uuid eq category.uuid, category)
    }

}