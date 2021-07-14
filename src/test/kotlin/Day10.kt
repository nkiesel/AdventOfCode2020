import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Day10 {
    @Test
    fun test() {
        val input = Path("input/10").readLines().map { it.toInt() }
        assertEquals(1920, one(input))
        assertEquals(1511207993344L, two(input))
        assertEquals(1511207993344L, two_A(input))
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

    private fun two_A(input: List<Int>): Long {
        val chains = mutableMapOf(0 to 1L)
        for (i in input.sorted()) chains[i] = (1..3).sumOf { chains.getOrDefault(i - it, 0L) }
        return chains[input.maxOf { it }]!!
    }


    /* Notes
        - As usual, solutions assume "good" data: no duplicates, no negative numbers etc.
        - We only need a "look-back" of 3 for problem 2 because any given number can only be used for at most the last 3 previous numbers
        - `two_A` was what came to me after I stared at my `two` solution for a bit. It wastes memory because it keeps all
          intermediate results instead of just the last 3 but with the provided input it is still "fast enough"
     */
}
