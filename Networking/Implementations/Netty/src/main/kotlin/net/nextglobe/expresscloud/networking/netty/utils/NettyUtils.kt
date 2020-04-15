package net.nextglobe.expresscloud.networking.netty.utils

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import java.util.UUID

object NettyUtils {

    fun newByteBuf(): ByteBuf = Unpooled.buffer()

    fun writeString(buf: ByteBuf, s: String) {
        val bytes = s.toByteArray()
        buf.writeInt(bytes.size)
        buf.writeBytes(bytes)
    }

    fun readString(buf: ByteBuf) : String {
        val bytes = ByteArray(buf.readInt())
        buf.readBytes(bytes)
        return String(bytes)
    }

    fun writeUUID(buf: ByteBuf, uuid: UUID) {
        buf.writeLong(uuid.mostSignificantBits)
        buf.writeLong(uuid.leastSignificantBits)
    }

    fun readUUID(buf: ByteBuf) : UUID {
        return UUID(buf.readLong(), buf.readLong())
    }

}