import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Day1 {
    @Test
    fun test() {
        val input = Path("input/1").readLines()
        assertEquals(970816, one(input))
        assertEquals(96047280, two(input))
    }

    private fun one(input: List<String>): Int {
        val expenses = input.map { it.toInt() }

        expenses.forEachIndexed { index1, line1 ->
            for (line2 in expenses.drop(index1 + 1)) {
                if (line1 + line2 == 2020) {
                    return line1 * line2
                }
            }
        }
        return 0
    }

    private fun two(input: List<String>): Int {
        val expenses = input.map { it.toInt() }

        expenses.forEachIndexed { index1, line1 ->
            expenses.drop(index1 + 1).forEachIndexed { index2, line2 ->
                expenses.drop(index2 + 1).forEachIndexed { _, line3 ->
                    if (line1 + line2 + line3 == 2020) {
                        return line1 * line2 * line3
                    }
                }
            }
        }
        return 0
    }
}
