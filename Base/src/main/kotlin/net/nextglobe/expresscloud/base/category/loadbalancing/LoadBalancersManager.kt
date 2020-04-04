package net.nextglobe.expresscloud.base.category.loadbalancing

import java.lang.IllegalArgumentException

object LoadBalancersManager {

    private val strategies: MutableMap<String, LoadBalancingStrategy> = mutableMapOf()

    fun get(name: String) : LoadBalancingStrategy? {
        return strategies[name]
    }

    fun add(strategy: LoadBalancingStrategy) {
        if(strategies.putIfAbsent(strategy.name, strategy) != null)
            throw IllegalArgumentException("A load balancing strategy with the provided name already exists")
    }

}