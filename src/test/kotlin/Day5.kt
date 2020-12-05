import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

@ExperimentalPathApi
class Day5 {
    @Test
    fun test() {
        val input = Path("input/5").readLines()
        assertEquals(935, one(input))
        assertEquals(743, two(input))
    }

    private fun one(input: List<String>): Int {
        fun seatId(code: String): Int {
            return code.toCharArray().fold(0) { acc, c -> acc * 2 + if (c == 'R' || c == 'B') 1 else 0 }
        }

        return input.maxOf { seatId(it) }
    }

    private fun two(input: List<String>): Int {
        fun seatId(code: String): Int {
            return code.toCharArray().fold(0) { acc, c -> acc * 2 + if (c == 'R' || c == 'B') 1 else 0 }
        }

        return input.map { seatId(it) }.sorted().zipWithNext().find { it.second != it.first + 1 }!!.first + 1
    }
}
