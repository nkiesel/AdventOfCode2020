import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Day2 {
    @Test
    fun test() {
        val input = Path("input/2").readLines()
        assertEquals(447, one(input))
        assertEquals(249, two(input))
    }

    private fun one(input: List<String>): Int {
        class Entry(line: String) {
            val password: String
            val letter: Char
            val range: IntRange

            init {
                val m = Regex("""(\d+)-(\d+) (\w): (\w+)""").matchEntire(line)!!.groupValues
                password = m[4]
                letter = m[3][0]
                range = m[1].toInt()..m[2].toInt()
            }

            fun isValid() = password.count { it == letter } in range
        }

        val entries = input.map { Entry(it) }

        return entries.count { it.isValid() }
    }

    private fun two(input: List<String>): Int {
        class Entry(line: String) {
            val password: String
            val letter: Char
            val positions: List<Int>

            init {
                val m = Regex("""(\d+)-(\d+) (\w): (\w+)""").matchEntire(line)!!.groupValues
                password = m[4]
                letter = m[3][0]
                positions = listOf(m[1].toInt() - 1, m[2].toInt() - 1)
            }

            fun isValid() = positions.count { password[it] == letter } == 1
        }

        val entries = input.map { Entry(it) }

        return entries.count { it.isValid() }
    }
}
