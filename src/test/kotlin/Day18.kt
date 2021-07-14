import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test


class Day18 {
    @Test
    fun test() {
        val input = Path("input/18").readLines()
        assertEquals(71L, one("1 + 2 * 3 + 4 * 5 + 6".lines()))
        assertEquals(51L, one("1 + (2 * 3) + (4 * (5 + 6))".lines()))
        assertEquals(26L, one("2 * 3 + (4 * 5)".lines()))
        assertEquals(437L, one("5 + (8 * 3 + 9 + 3 * 4 * 3)".lines()))
        assertEquals(12240L, one("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))".lines()))
        assertEquals(13632L, one("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2".lines()))
        assertEquals(25190263477788L, one(input))

        assertEquals(231L, two("1 + 2 * 3 + 4 * 5 + 6".lines()))
        assertEquals(51L, two("1 + (2 * 3) + (4 * (5 + 6))".lines()))
        assertEquals(46L, two("2 * 3 + (4 * 5)".lines()))
        assertEquals(1445L, two("5 + (8 * 3 + 9 + 3 * 4 * 3)".lines()))
        assertEquals(669060L, two("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))".lines()))
        assertEquals(23340L, two("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2".lines()))
        assertEquals(25190263477788L, two(input))
    }

    private fun one(input: List<String>): Long {

        fun evaluate(expression: String): Long {
            var idx = 0

            fun evaluate(): Long {
                var result = 0L
                var op = ' '

                fun compute(n: Long) {
                    when (op) {
                        ' ' -> result = n
                        '+' -> result += n
                        '*' -> result *= n
                    }
                    op = ' '
                }

                while (idx <= expression.lastIndex) {
                    when (val c = expression[idx++]) {
                        in '0'..'9' -> compute(c.toString().toLong())
                        '+' -> op = c
                        '*' -> op = c
                        '(' -> compute(evaluate())
                        ')' -> return result
                    }
                }
                return result
            }
            return evaluate()
        }

        return input.sumOf { evaluate(it) }
    }

    private fun two(input: List<String>): Long {
        TODO("Not yet implemented")
    }

}
