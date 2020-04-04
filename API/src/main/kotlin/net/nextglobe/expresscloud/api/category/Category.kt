package net.nextglobe.expresscloud.api.category

import net.nextglobe.expresscloud.api.server.CategoryServer

interface Category {

    val name : String
    val paths : CategoryPaths
    val ram : Int

    fun createServer() : CategoryServer
    suspend fun createAndStartServer() : CategoryServer

}