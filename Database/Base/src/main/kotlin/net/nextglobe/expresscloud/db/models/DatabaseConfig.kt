package net.nextglobe.expresscloud.db.models

import kotlinx.serialization.Serializable
import net.nextglobe.expresscloud.db.UUIDSerializer
import java.util.UUID

@Serializable
data class DatabaseConfig(
    @Serializable(with = UUIDSerializer::class)
    val uuid: UUID,
    val name: String,
    val cloudManagerImplementation: String
)