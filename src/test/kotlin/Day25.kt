import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test


class Day25 {
    @Test
    fun test() {
        val input = Path("input/25").readLines()
        assertEquals(14897079L, one("""
            5764801
            17807724
        """.trimIndent().lines()))
        assertEquals(12285001L, one(input))
    }

    private fun one(input: List<String>): Long {
        data class Device(val publicKey: Long, var loops: Int = 0)
        val devices = input.map { Device(it.toLong()) }
        var loop = 1
        var value = 1L
        fun Long.next(subjectNumber: Long) = (this * subjectNumber) % 20201227L
        while (devices.all { it.loops == 0 }) {
            value = value.next(7L)
            devices.forEach { device ->
                if (device.loops == 0 && value == device.publicKey) device.loops = loop
            }
            loop++
        }

        val device = devices.first { it.loops > 0 }
        val subjectNumber = devices.first { it != device }.publicKey
        var encryptionKey = 1L
        repeat(device.loops) {
            encryptionKey = encryptionKey.next(subjectNumber)
        }

        return encryptionKey
    }

}

