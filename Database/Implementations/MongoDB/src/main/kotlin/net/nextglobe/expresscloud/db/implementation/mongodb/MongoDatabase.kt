package net.nextglobe.expresscloud.db.implementation.mongodb

import net.nextglobe.expresscloud.db.Database
import net.nextglobe.expresscloud.db.models.DatabaseCategory
import net.nextglobe.expresscloud.db.models.DatabaseCloudConfig
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import java.net.URI
import java.util.UUID

class MongoDatabase : Database {

    private lateinit var client: CoroutineClient
    private lateinit var database: CoroutineDatabase

    private lateinit var databaseCloudConfigsCollection: CoroutineCollection<DatabaseCloudConfig>
    private lateinit var databaseCategoriesCollection: CoroutineCollection<DatabaseCategory>

    override fun connect(connectionString: String, database: String) {
        this.client = KMongo.createClient(connectionString).coroutine
        this.database = client.getDatabase(database)

        this.databaseCloudConfigsCollection = this.database.getCollection("databaseCloudConfigs")
        this.databaseCategoriesCollection = this.database.getCollection("databaseCategories")
    }

    override fun buildConnectionString(
        username: String?,
        password: String?,
        hostname: String?,
        port: Int?,
        authenticationDatabase: String?
    ) = URI("mongodb",
        if (username.isNullOrBlank()) null else if(password.isNullOrBlank()) username else "$username:$password",
        if (hostname.isNullOrBlank()) "localhost" else hostname,
        port ?: 27017,
        if(authenticationDatabase.isNullOrBlank()) null else "/$authenticationDatabase",
        null,
        null
    ).toString()

    override suspend fun getDatabaseCloudConfigs(): List<DatabaseCloudConfig> {
        return databaseCloudConfigsCollection.find().toList()
    }

    override suspend fun getDatabaseCloudConfig(uuid: UUID): DatabaseCloudConfig? {
        return databaseCloudConfigsCollection.find(DatabaseCloudConfig::uuid eq uuid).first()
    }

    override suspend fun insertDatabaseCloudConfig(cloudConfig: DatabaseCloudConfig) {
        databaseCloudConfigsCollection.insertOne(cloudConfig)
    }

    override suspend fun updateDatabaseCloudConfig(cloudConfig: DatabaseCloudConfig) {
        databaseCloudConfigsCollection.updateOne(DatabaseCloudConfig::uuid eq cloudConfig.uuid, cloudConfig)
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
        databaseCategoriesCollection.updateOne(DatabaseCloudConfig::uuid eq category.uuid, category)
    }

}