package net.nextglobe.expresscloud.base.category.loadbalancing

interface LoadBalancingStrategy {

    val name: String

    fun shouldStartServer() : Boolean

}