import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Day3 {
    @Test
    fun test() {
        val input = Path("input/3").readLines()
        assertEquals(167, one(input))
        assertEquals(736527114, two(input))
    }

    private fun one(input: List<String>): Int {
        var column = 0
        var row = 0
        val columnIncrement = 3
        val rowIncrement = 1
        var trees = 0

        while (true) {
            row += rowIncrement
            if (row !in input.indices) {
                return trees
            }
            val currentRow = input[row]
            column = (column + columnIncrement) % currentRow.length
            if (currentRow[column] == '#') trees++
        }
    }

    private fun two(input: List<String>): Int {
        fun slope(columnIncrement: Int, rowIncrement: Int): Int {
            var column = 0
            var row = 0
            var trees = 0
            while (true) {
                row += rowIncrement
                if (row !in input.indices) {
                    return trees
                }
                val currentRow = input[row]
                column = (column + columnIncrement) % currentRow.length
                if (currentRow[column] == '#') trees++
            }
        }

        return slope(1, 1) * slope(3, 1) * slope(5, 1) * slope(7, 1) * slope(1, 2)
    }
}
