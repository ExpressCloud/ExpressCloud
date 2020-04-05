package net.nextglobe.expresscloud.db.models

import kotlinx.serialization.Serializable
import net.nextglobe.expresscloud.api.exception.CloudException
import net.nextglobe.expresscloud.db.UUIDSerializer
import net.nextglobe.expresscloud.db.exception.NoActiveDatabaseCloudConfigsException
import net.nextglobe.expresscloud.db.exception.TooManyActiveDatabaseCloudConfigsException
import java.util.UUID

@Serializable
data class DatabaseCloudConfig(
    @Serializable(with = UUIDSerializer::class)
    val uuid: UUID,
    val name: String,
    val active: Boolean,
    val cloudManagerImplementation: String,
    val cloudManagerConnectionString: String?
) {
    companion object {
        fun getActiveDatabaseCloudConfig(cloudConfigs: List<DatabaseCloudConfig>) : DatabaseCloudConfig? {
            return cloudConfigs.find { it.active }
        }

        fun getActiveDatabaseCloudConfigStrict(cloudConfigs: List<DatabaseCloudConfig>) : DatabaseCloudConfig {
            val activeDatabaseCloudConfigs = cloudConfigs.filter { it.active }
            if(activeDatabaseCloudConfigs.size > 1)
                throw TooManyActiveDatabaseCloudConfigsException()
            else if(activeDatabaseCloudConfigs.isEmpty())
                throw NoActiveDatabaseCloudConfigsException()
            return activeDatabaseCloudConfigs.first()
        }
    }
}