package net.nextglobe.expresscloud.db.models

import kotlinx.serialization.Serializable
import net.nextglobe.expresscloud.api.category.CategoryPaths
import net.nextglobe.expresscloud.db.UUIDSerializer
import java.util.UUID

@Serializable
data class DatabaseCategory(
    @Serializable(with = UUIDSerializer::class)
    val uuid: UUID,
    val name: String,
    val paths: CategoryPaths,
    val ram: Int,
    val loadBalancingStrategy: String?
)