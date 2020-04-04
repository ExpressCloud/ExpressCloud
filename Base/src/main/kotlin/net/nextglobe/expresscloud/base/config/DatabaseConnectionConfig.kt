package net.nextglobe.expresscloud.base.config

data class DatabaseConnectionConfig(
    val username: String?,
    val password: String?,
    val authenticationDatabase: String?,
    val database: String?,
    val hostname: String?,
    val port: Int?,
    val connectionString: String?
)