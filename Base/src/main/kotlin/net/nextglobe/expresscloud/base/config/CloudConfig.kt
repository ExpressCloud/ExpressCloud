package net.nextglobe.expresscloud.base.config

data class CloudConfig(
    val databaseImplementation: String,
    val databaseConnectionConfiguration: DatabaseConnectionConfig
)