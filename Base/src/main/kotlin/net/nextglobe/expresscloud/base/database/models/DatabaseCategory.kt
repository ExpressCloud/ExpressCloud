package net.nextglobe.expresscloud.base.database.models

import net.nextglobe.expresscloud.api.category.CategoryPaths
import net.nextglobe.expresscloud.base.category.Category
import net.nextglobe.expresscloud.base.category.loadbalancing.LoadBalancersManager
import java.util.UUID

data class DatabaseCategory(
    val uuid: UUID,
    val name: String,
    val paths: CategoryPaths,
    val ram: Int,
    val loadBalancingStrategy: String?
)