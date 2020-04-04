package net.nextglobe.expresscloud.api.server

import java.util.UUID

interface Server {
    val id: UUID
    val name: String

    fun starting() : Boolean
    fun started() : Boolean
    fun stopping() : Boolean
    fun stopped() : Boolean
    fun deleted() : Boolean

    suspend fun start() : Boolean
    suspend fun stop() : Boolean
}