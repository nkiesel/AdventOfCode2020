import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

@ExperimentalPathApi
class Day9 {
    @Test
    fun test() {
        val input = Path("input/9").readLines().map { it.toLong() }
        assertEquals(22477624L, one(input, 25))
        assertEquals(2980044L, two(input, 22477624L))
    }

    private fun one(input: List<Long>, window: Int): Long {
        fun notSum(v: Long, l: List<Long>): Boolean {
            val s = l.toSet()
            return l.none { v != it * 2 && s.contains(v - it) }
        }

        input.windowed(window).forEachIndexed { index, list ->
            val candidate = input[index + window]
            if (notSum(candidate, list)) return candidate
        }
        return 0
    }

    private fun two(input: List<Long>, value: Long): Long {
        var s = 0
        var e = 0
        var sum = 0L
        while (sum != value) {
            if (sum < value) sum += input[e++]
            else sum -= input[s++]
        }
        val l = input.subList(s, e)
        return l.minOrNull()!! + l.maxOrNull()!!
    }

    /* Notes
     - the `notSum` implementation uses the rule that the 2 numbers that are added will have different values and
      thus knows that `it * 2` cannot be a possible solution. That way, we do not have to eliminate the first number
      from the list of candidates.
     - `two` would fail if `input` would contain negative numbers (because shrinking the window would not assure that
       the sum would be reduced).  This is not mentioned in the rules but a manual scan of the input data showed only
       positive numbers.
     */

}
