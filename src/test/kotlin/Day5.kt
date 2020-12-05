import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

@ExperimentalPathApi
class Day5 {
    @Test
    fun test() {
        assertEquals(935, one())
        assertEquals(743, two())
    }

    private fun one(): Int {
        val input = Path("input/5").readLines()

        fun seatId(code: String): Int {
            return code.toCharArray().fold(0) { acc, c -> acc * 2 + if (c == 'R' || c == 'B') 1 else 0 }
        }

        return input.maxOf { seatId(it) }
    }

    private fun two(): Int {
        val input = Path("input/5").readLines()

        fun seatId(code: String): Int {
            return code.toCharArray().fold(0) { acc, c -> acc * 2 + if (c == 'R' || c == 'B') 1 else 0 }
        }

        return input.map { seatId(it) }.sorted().zipWithNext().find { it.second != it.first + 1 }!!.first + 1
    }
}
