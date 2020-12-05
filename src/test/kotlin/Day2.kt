import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

@ExperimentalPathApi
class Day2 {
    @Test
    fun test() {
        assertEquals(447, one())
        assertEquals(249, two())
    }

    private fun one(): Int {
        class Entry(line: String) {
            val range: IntRange
            val c: Char
            val password: String
            init {
                val m = Regex("(\\d+)-(\\d+) (\\w): (\\w+)").matchEntire(line)!!.groupValues
                range = m[1].toInt()..m[2].toInt()
                c = m[3][0]
                password = m[4]
            }
        }

        val input = Path("input/2").readLines().map { Entry(it) }

        return input.map { entry ->
            val count = entry.password.toCharArray().count { it == entry.c }
            if (count in entry.range) 1 else 0
        }.sum()
    }

    private fun two(): Int {
        class Entry(line: String) {
            val positions: List<Int>
            val c: Char
            val password: String
            init {
                val m = Regex("(\\d+)-(\\d+) (\\w): (\\w+)").matchEntire(line)!!.groupValues
                positions = listOf(m[1].toInt() - 1, m[2].toInt() - 1)
                c = m[3][0]
                password = m[4]
            }
        }

        val input = Path("input/2").readLines().map { Entry(it) }

        return input.map { entry ->
            val count = entry.positions.count { entry.password[it] == entry.c }
            if (count == 1) 1 else 0
        }.sum()
    }
}
