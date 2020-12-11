import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test


@ExperimentalPathApi
class Day11 {
    @Test
    fun test() {
        val input = Path("input/11").readLines()
        val test = """
            L.LL.LL.LL
            LLLLLLL.LL
            L.L.L..L..
            LLLL.LL.LL
            L.LL.LL.LL
            L.LLLLL.LL
            ..L.L.....
            LLLLLLLLLL
            L.LLLLLL.L
            L.LLLLL.LL
        """.trimIndent().lines()
        assertEquals(37, one(test))
        assertEquals(2316, one(input))
        assertEquals(26, two(test))
        assertEquals(2128, two(input))
    }


    class Room(private val data: Array<CharArray>) {
        fun clone(): Room = Room(data.map { it.copyOf() }.toTypedArray())

        fun adjacent(row: Int, col: Int) = (-1..1).sumOf { r ->
            (-1..1).count { c ->
                !(r == 0 && c == 0) && data[row + r][col + c] == '#'} }

        private fun visible(row: Int, col: Int, r: Int, c: Int): Boolean {
            var ri = row + r
            var ci = col + c
            while (ri in rowIndices && ci in colIndices) {
                when (data[ri][ci]) {
                    '#' -> return true
                    'L' -> return false
                }
                ri += r
                ci += c
            }
            return false
        }

        fun visible(row: Int, col: Int) = (-1..1).sumOf { r ->
            (-1..1).count { c ->
                !(r == 0 && c == 0) && visible(row, col, r, c) } }

        fun show() {
            data.forEach { println(it.joinToString("")) }
            println("-".repeat(data[0].size))
        }

        val occupancy: Int by lazy { data.sumOf { row -> row.count { it == '#' } } }

        fun get(row: Int, col: Int) = data[row][col]

        fun set(row: Int, col: Int, c: Char) {
            data[row][col] = c
        }

        val rowIndices = 1..(data.size - 2)
        val colIndices = 1..(data[0].size - 2)

        companion object {
            fun fromInput(input: List<String>): Room {
                val dots = listOf(".".repeat(input.first().length))
                return Room((dots + input + dots).map { line -> ".$line.".toCharArray() }.toTypedArray())
            }
        }
    }

    private fun one(input: List<String>): Int {

        var room = Day11.Room.fromInput(input)
  //      room.show()

        while (true) {
            val clone = room.clone()
            for (r in room.rowIndices) {
                for (c in room.colIndices) {
                    when (room.get(r, c)) {
                        'L' -> if (room.adjacent(r, c) == 0) clone.set(r, c, '#')
                        '#' -> if (room.adjacent(r, c) >= 4) clone.set(r, c, 'L')
                    }
                }
            }
//            clone.show()
            if (room.occupancy == clone.occupancy) return clone.occupancy
            room = clone
        }
    }

    private fun two(input: List<String>): Int {
        var room = Room.fromInput(input)
        while (true) {
            val clone = room.clone()
            for (r in room.rowIndices) {
                for (c in room.colIndices) {
                    when (room.get(r, c)) {
                        'L' -> if (room.visible(r, c) == 0) clone.set(r, c, '#')
                        '#' -> if (room.visible(r, c) >= 5) clone.set(r, c, 'L')
                    }
                }
            }
//            clone.show()
            if (room.occupancy == clone.occupancy) return clone.occupancy
            room = clone
        }
    }

}
