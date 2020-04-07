package net.nextglobe.expresscloud.implementation.docker.utils

import net.nextglobe.expresscloud.implementation.docker.logger
import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream
import java.io.File

class TarUtils {
    companion object {
        fun addDirectoryToTar(path: String?, name: String, archiveOutputStream: TarArchiveOutputStream) {
            if(path != null)
                File(path).let { file ->
                    if(file.exists() && file.isDirectory) {
                        archiveOutputStream.putArchiveEntry(TarArchiveEntry(file, name))
                        archiveOutputStream.closeArchiveEntry()
                    } else {
                        logger.warn { "The provided $name folder does not exist! [$path]" }
                    }
                }
        }
    }
}