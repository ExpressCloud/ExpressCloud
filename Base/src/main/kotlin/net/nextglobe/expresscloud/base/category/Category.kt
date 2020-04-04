package net.nextglobe.expresscloud.base.category

import net.nextglobe.expresscloud.api.category.Category
import net.nextglobe.expresscloud.api.category.CategoryPaths
import net.nextglobe.expresscloud.base.category.loadbalancing.LoadBalancersManager
import net.nextglobe.expresscloud.base.category.loadbalancing.LoadBalancingStrategy
import net.nextglobe.expresscloud.base.database.models.DatabaseCategory
import net.nextglobe.expresscloud.base.server.Server
import java.util.*
import kotlin.math.floor

class Category(override val ram: Int,
               override val name: String,
               override val paths: CategoryPaths,
               private val loadBalancingStrategy : LoadBalancingStrategy?
) : Category {

    private val servers : MutableList<Server> = mutableListOf()

    suspend fun addServer(server: Server, loadBalance: Boolean = true) {
        servers.add(server)
        if(loadBalance) loadBalance()
    }

    suspend fun removeServer(serverId: UUID, loadBalance: Boolean = true) {
        servers.removeIf { it.id == serverId }
        if(loadBalance) loadBalance()
    }

    suspend fun removeServer(server: net.nextglobe.expresscloud.api.server.Server, loadBalancing: Boolean = true) {
        removeServer(server.id, loadBalancing)
    }

    override fun createServer(): Server {
        return Server(category = this, number = getNextFreeNumber())
    }

    override suspend fun createAndStartServer(): Server {
        return createServer().apply { start() }
    }

    suspend fun loadBalance() {
        if(loadBalancingStrategy?.shouldStartServer() == true)
            addServer(createAndStartServer())
    }

    fun getOnlineServers() : List<Server> {
        return getStartedServers()
    }

    fun getStartedServers() : List<Server> {
        return servers.filter { it.started() }
    }

    private fun getNextFreeNumber(min: Int = 0, max: Int = servers.count()) : Int {
        if(min >= max)
            return min + 1
        val pivot = floor((min + max).toDouble() / 2).toInt()
        if(servers[pivot].number == pivot + 1)
            return getNextFreeNumber(pivot + 1, max)
        return getNextFreeNumber(min, pivot)
    }

    companion object {
        fun ofDatabaseCategory(databaseCategory: DatabaseCategory) : Category {
            return Category(databaseCategory.ram, databaseCategory.name, databaseCategory.paths, databaseCategory.loadBalancingStrategy?.let { LoadBalancersManager.get(it) })
        }
    }

}