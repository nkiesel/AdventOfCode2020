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

        fun adjacent(row: Int, col: Int) = (-1..1).sumOf { r -> (-1..1).count { c -> adjacent(row, col, r, c) } }

        private fun adjacent(row: Int, col: Int, r: Int, c: Int): Boolean {
            if (r == 0 && c == 0) return false
            val ri = row + r
            val ci = col + c
            return (ri in rowIndices && ci in colIndices) && data[ri][ci] == '#'
        }

        fun visible(row: Int, col: Int) = (-1..1).sumOf { r -> (-1..1).count { c -> visible(row, col, r, c) } }

        private fun visible(row: Int, col: Int, r: Int, c: Int): Boolean {
            if (r == 0 && c == 0) return false
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

        fun show() {
            data.forEach { println(it.joinToString("")) }
            println("-".repeat(data[0].size))
        }

        val occupancy: Int by lazy { data.sumOf { row -> row.count { it == '#' } } }

        fun get(row: Int, col: Int) = data[row][col]

        fun set(row: Int, col: Int, c: Char) {
            data[row][col] = c
        }

        val rowIndices = data.indices
        val colIndices = data[0].indices

        companion object {
            fun fromInput(input: List<String>): Room {
                return Room((input).map { it.toCharArray() }.toTypedArray())
            }
        }
    }

    private fun one(input: List<String>): Int {
        var room = Room.fromInput(input)

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
            if (room.occupancy == clone.occupancy) return clone.occupancy
            room = clone
        }
    }

}
