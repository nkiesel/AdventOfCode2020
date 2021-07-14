import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test


class Day20 {
    @Test
    fun test() {
        val input = Path("input/20").readLines()
        val test = """
            Tile 2311:
            ..##.#..#.
            ##..#.....
            #...##..#.
            ####.#...#
            ##.##.###.
            ##...#.###
            .#.#.#..##
            ..#....#..
            ###...#.#.
            ..###..###

            Tile 1951:
            #.##...##.
            #.####...#
            .....#..##
            #...######
            .##.#....#
            .###.#####
            ###.##.##.
            .###....#.
            ..#.#..#.#
            #...##.#..

            Tile 1171:
            ####...##.
            #..##.#..#
            ##.#..#.#.
            .###.####.
            ..###.####
            .##....##.
            .#...####.
            #.##.####.
            ####..#...
            .....##...

            Tile 1427:
            ###.##.#..
            .#..#.##..
            .#.##.#..#
            #.#.#.##.#
            ....#...##
            ...##..##.
            ...#.#####
            .#.####.#.
            ..#..###.#
            ..##.#..#.

            Tile 1489:
            ##.#.#....
            ..##...#..
            .##..##...
            ..#...#...
            #####...#.
            #..#.#.#.#
            ...#.#.#..
            ##.#...##.
            ..##.##.##
            ###.##.#..

            Tile 2473:
            #....####.
            #..#.##...
            #.##..#...
            ######.#.#
            .#...#.#.#
            .#########
            .###.#..#.
            ########.#
            ##...##.#.
            ..###.#.#.

            Tile 2971:
            ..#.#....#
            #...###...
            #.#.###...
            ##.##..#..
            .#####..##
            .#..####.#
            #..#.#..#.
            ..####.###
            ..#.#.###.
            ...#.#.#.#

            Tile 2729:
            ...#.#.#.#
            ####.#....
            ..#.#.....
            ....#..#.#
            .##..##.#.
            .#.####...
            ####.#.#..
            ##.####...
            ##..#.##..
            #.##...##.

            Tile 3079:
            #.#.#####.
            .#..######
            ..#.......
            ######....
            ####.#..#.
            .#...#.##.
            #.#####.##
            ..#.###...
            ..#.......
            ..#.###...
        """.trimIndent().lines()
        assertEquals(20899048083289L, one(test))
        assertEquals(18411576553343L, one(input))
    }

    private fun one(input: List<String>): Long {
        class Tile(data: List<String>) {
            val id = Regex("""Tile (\d+):""").matchEntire(data.first())!!.groupValues[1].toInt()
            val cells = data.drop(1)
            fun edges(): List<String> = buildList {
                add(cells.first())
                add(cells.first().reversed())
                add(cells.last())
                add(cells.last().reversed())
                add(cells.map { it[0] }.joinToString(""))
                add(cells.map { it[0] }.joinToString("").reversed())
                val last = cells.first().lastIndex
                add(cells.map { it[last] }.joinToString(""))
                add(cells.map { it[last] }.joinToString("").reversed())
            }
        }

        val tiles = input.chunkedBy { it.isEmpty() }.mapNotNull { if (it.isNotEmpty()) Tile(it) else null }
        val allEdges = tiles.map { it.edges() }.flatten().groupingBy { it }.eachCount()
        return tiles.filter { it.edges().count { edge -> allEdges[edge] == 1 } == 4 }.fold(1L) { acc, tile -> acc * tile.id }
    }

}
