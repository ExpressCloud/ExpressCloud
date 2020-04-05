package net.nextglobe.expresscloud.db.models

import kotlinx.serialization.Serializable
import net.nextglobe.expresscloud.api.exception.CloudException
import net.nextglobe.expresscloud.db.UUIDSerializer
import java.util.UUID

@Serializable
data class DatabaseCloudConfig(
    @Serializable(with = UUIDSerializer::class)
    val uuid: UUID,
    val name: String,
    val active: Boolean,
    val cloudManagerImplementation: String
) {
    companion object {
        fun getActiveDatabaseCloudConfig(cloudConfigs: List<DatabaseCloudConfig>) : DatabaseCloudConfig? {
            return cloudConfigs.find { it.active }
        }

        fun getActiveDatabaseCloudConfigStrict(cloudConfigs: List<DatabaseCloudConfig>) : DatabaseCloudConfig {
            val activeDatabaseCloudConfigs = cloudConfigs.filter { it.active }
            if(activeDatabaseCloudConfigs.size > 1)
                throw CloudException("More than one database cloud configs are active. Only one config can be active at a time")
            else if(activeDatabaseCloudConfigs.isEmpty())
                throw CloudException("No database cloud config is active. Exactly one config must be active at a time")
            return activeDatabaseCloudConfigs.first()
        }
    }
}