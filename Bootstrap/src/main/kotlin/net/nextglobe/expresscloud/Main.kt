package net.nextglobe.expresscloud

import mu.KotlinLogging
import net.nextglobe.expresscloud.base.Cloud

val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    try {
        logger.info { "ExpressCloud is starting..." }
        Cloud.start()
        logger.info { "ExpressCloud started. Use the CLI client or the web interface addon to manage/configure the cloud" }
    } catch(e: Exception) {
        logger.error(e) { "An error occurred during starting the cloud!" }
    }
}