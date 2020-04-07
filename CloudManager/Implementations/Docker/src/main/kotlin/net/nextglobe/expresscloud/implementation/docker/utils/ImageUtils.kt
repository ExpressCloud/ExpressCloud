package net.nextglobe.expresscloud.implementation.docker.utils

class ImageUtils {
    companion object {
        fun createServerImage(baseImage: String, cmd: String = "java -jar server.jar") : String {
            return buildString {
                append("FROM ").append(baseImage).append("\n")
                append("WORKDIR /cloud/server").append("\n")
                append("COPY server.jar plugins .").append("\n")
                append("COPY worlds configs ./").append("\n")
                append("CMD [").append(cmd.split(" ").joinToString(", ", "\"", "\"")).append("]")
            }
        }
    }
}