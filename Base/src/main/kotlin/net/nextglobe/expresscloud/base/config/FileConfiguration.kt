package net.nextglobe.expresscloud.config

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigRenderOptions
import java.awt.SystemColor.text
import java.io.PrintWriter
import java.nio.file.Path


class FileConfiguration(private val path: Path) {

    fun load(): Config {
        return ConfigFactory.parseFile(path.toFile())
    }

    fun save(config: Config) {
        if(!exists())
            path.toFile().createNewFile()
        PrintWriter(path.toFile()).use {
            it.println(config.root().render(ConfigRenderOptions.defaults().setOriginComments(false)))
        }
    }

    fun exists(): Boolean {
        return path.toFile().exists()
    }

}