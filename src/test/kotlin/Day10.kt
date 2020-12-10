import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

@ExperimentalPathApi
class Day10 {
    @Test
    fun test() {
        val input = Path("input/10").readLines().map { it.toInt() }
        assertEquals(1920, one(input))
        assertEquals(1511207993344L, two(input))
    }

    private fun one(input: List<Int>): Int {
        var d1 = 0
        var d3 = 1
        input.sorted().fold(0) { acc, i ->
            when (i - acc) {
                1 -> d1++
                3 -> d3++
            }
            i
        }
        return d1 * d3
    }

    private fun two(input: List<Int>): Long {
        class Chain(val last: Int, val count: Long) {
            fun possible(i: Int) = if (i - last <= 3) count else 0L
        }
        val chains = ArrayDeque<Chain>(3)

        fun add(c: Chain) {
            if (chains.size >= 3) chains.removeFirst()
            chains.add(c)
        }

        add(Chain(0, 1L))
        input.sorted().forEach { i ->
            val possible = chains.sumOf { it.possible(i) }
            add(Chain(i, possible))
        }
        return chains.last().count
    }

    /* Notes
        - as usual, solutions assume "good" data: no duplicates, no negative numbers etc.
        - we only need a "lookback" of 3 for problem 2 because any given number can only be used for at most the last 3 previous numbers
     */
}
