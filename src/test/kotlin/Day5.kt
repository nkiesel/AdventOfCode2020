import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Day5 {
    @Test
    fun test() {
        val input = Path("input/5").readLines()
        assertEquals(935, one(input))
        assertEquals(935, vn_one(input))
        assertEquals(743, two(input))
    }

    private val m = mapOf('R' to 1, 'L' to 0, 'B' to 1, 'F' to 0)

    private fun seatId(code: String): Int {
        return code.fold(0) { acc, c -> acc * 2 + m[c]!! }
    }

    private fun one(input: List<String>): Int {
        return input.maxOf { seatId(it) }
    }

    private fun two(input: List<String>): Int {
        return input.map { seatId(it) }.sorted().zipWithNext().find { it.second != it.first + 1 }!!.first + 1
    }

    data class Seat(val row: Int, val column: Int, val id: Int)

    private fun vn_one(input: List<String>): Int {
        val seats = input.map { line -> computeSeat(line) }
        return seats.maxOf { it.id }
    }

    private fun computeSeat(input:String): Seat {
        var startRow = 0
        var endRow = 127
        var startColumn = 0
        var endColumn = 7
        for (item in input) {
            when (item) {
                'B' -> startRow = ((startRow + endRow) / 2) + 1
                'F' -> endRow = (startRow + endRow) / 2
                'L' -> endColumn = (startColumn + endColumn) / 2
                'R' -> startColumn = ((startColumn + endColumn) / 2) + 1
            }
        }
        val seatId = (endRow * 8) + endColumn
        return Seat(endRow, endColumn, seatId)
    }
}
