import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Day5 {
    @Test
    fun test() {
        val input = Path("input/5").readLines()
        assertEquals(935, one(input))
        assertEquals(743, two(input))
    }

    private val m = mapOf('R' to 1, 'L' to 0, 'B' to 1, 'F' to 0)

    private fun seatId(code: String): Int {
        return code.fold(0) { acc, c -> acc * 2 + m[c]!! }
    }

    private fun one(input: List<String>): Int {
        return input.maxOf { seatId(it) }
    }

    private fun two(input: List<String>): Int {
        return input.map { seatId(it) }.sorted().zipWithNext().find { it.second != it.first + 1 }!!.first + 1
    }
}
