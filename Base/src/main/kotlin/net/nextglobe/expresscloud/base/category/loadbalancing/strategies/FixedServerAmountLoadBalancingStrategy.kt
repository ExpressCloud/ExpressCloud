package net.nextglobe.expresscloud.base.category.loadbalancing.strategies

import net.nextglobe.expresscloud.base.category.Category

class FixedServerAmountLoadBalancingStrategy(category: Category, private val serverAmount: Int) : GenericLoadBalancingStrategy<Category>(category) {

    override val name: String = "fixed-server-amount"

    override fun shouldStartServer(argument: Category): Boolean {
        return serverAmount < argument.getOnlineServers().count()
    }

}