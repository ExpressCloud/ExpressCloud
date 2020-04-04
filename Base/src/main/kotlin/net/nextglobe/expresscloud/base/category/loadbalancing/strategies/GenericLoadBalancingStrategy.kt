package net.nextglobe.expresscloud.base.category.loadbalancing.strategies

import net.nextglobe.expresscloud.base.category.loadbalancing.LoadBalancingStrategy

abstract class GenericLoadBalancingStrategy<in Argument>(private val argument: Argument) : LoadBalancingStrategy {

    override fun shouldStartServer(): Boolean {
        return shouldStartServer(argument)
    }

    abstract fun shouldStartServer(argument: Argument) : Boolean

}