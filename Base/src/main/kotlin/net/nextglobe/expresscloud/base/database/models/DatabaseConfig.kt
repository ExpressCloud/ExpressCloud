package net.nextglobe.expresscloud.base.database.models

import java.util.UUID

data class DatabaseConfig(
    val uuid: UUID,
    val name: String,
    val cloudManagerImplementation: String
)