import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Day1 {
    @Test
    fun test() {
        val input = Path("input/1").readLines()
        assertEquals(970816, one(input, 2020))
        assertEquals(970816, oneK(input, 2020))
        assertEquals(96047280, two(input, 2020))
    }

    private fun one(input: List<String>, sum: Int): Int? {
        val expenses = input.map { it.toInt() }

        expenses.forEachIndexed { index1, line1 ->
            for (line2 in expenses.drop(index1 + 1)) {
                if (line1 + line2 == sum) {
                    return line1 * line2
                }
            }
        }
        return null
    }

    // Solution inspired by code from https://blog.jetbrains.com/kotlin/2021/07/advent-of-code-in-idiomatic-kotlin/
    private fun oneK(input: List<String>, sum: Int): Int? {
        val expenses = input.map(String::toInt)
        // The filter below ensures that we ignore the first 1010 we encounter
        var seen = 0
        val complements = expenses.filterNot { sum == it * 2 && seen++ == 0 }.associateBy { sum - it }
        return expenses.firstNotNullOfOrNull { number -> complements[number]?.let { number * it } }
    }

    private fun two(input: List<String>, sum: Int): Int? {
        val expenses = input.map { it.toInt() }

        expenses.forEachIndexed { index1, line1 ->
            expenses.drop(index1 + 1).forEachIndexed { index2, line2 ->
                expenses.drop(index2 + 1).forEachIndexed { _, line3 ->
                    if (line1 + line2 + line3 == sum) {
                        return line1 * line2 * line3
                    }
                }
            }
        }
        return null
    }
}
