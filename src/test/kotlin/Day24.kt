import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test


class Day24 {
    @Test
    fun test() {
        val input = Path("input/24").readLines()
        val test = """
            sesenwnenenewseeswwswswwnenewsewsw
            neeenesenwnwwswnenewnwwsewnenwseswesw
            seswneswswsenwwnwse
            nwnwneseeswswnenewneswwnewseswneseene
            swweswneswnenwsewnwneneseenw
            eesenwseswswnenwswnwnwsewwnwsene
            sewnenenenesenwsewnenwwwse
            wenwwweseeeweswwwnwwe
            wsweesenenewnwwnwsenewsenwwsesesenwne
            neeswseenwwswnwswswnw
            nenwswwsewswnenenewsenwsenwnesesenew
            enewnwewneswsewnwswenweswnenwsenwsw
            sweneswneswneneenwnewenewwneswswnese
            swwesenesewenwneswnwwneseswwne
            enesenwswwswneneswsenwnewswseenwsese
            wnwnesenesenenwwnenwsewesewsesesew
            nenewswnwewswnenesenwnesewesw
            eneswnwswnwsenenwnwnwwseeswneewsenese
            neswnwewnwnwseenwseesewsenwsweewe
            wseweeenwnesenwwwswnew
        """.trimIndent().lines()
        assertEquals(1, one("""
            esew
        """.trimIndent().lines()))
        assertEquals(10, one(test))
        assertEquals(473, one(input))
        assertEquals(2208, two(test))
        assertEquals(4070, two(input))
    }

    private fun one(input: List<String>): Int {
        val blackTiles = mutableSetOf<Pair<Int, Int>>()

        fun flip(x: Int, y: Int) {
            blackTiles.remove(x to y) || blackTiles.add(x to y)
        }

        val offsets = mapOf(
            "e" to Pair(2, 0),
            "w" to Pair(-2, 0),
            "ne" to Pair(1, 1),
            "se" to Pair(1, -1),
            "nw" to Pair(-1, 1),
            "sw" to Pair(-1, -1),
        )

        input.forEach { line ->
            val seq = sequence {
                var idx = 0
                while (idx in line.indices) {
                    yield(offsets[when (val c1 = line[idx++]) {
                            'e', 'w' -> "$c1"
                            else -> "$c1${line[idx++]}"
                        }])
                }
            }

            var x = 0
            var y = 0
            seq.forEach { d ->
                if (d != null) {
                    x += d.first
                    y += d.second
                }
            }
            flip(x, y)
        }
        return blackTiles.size
    }

    private fun two(input: List<String>): Int {

        data class Tile(val x: Int, val y:Int) {
            fun neighbor(direction: String): Tile {
                return when(direction) {
                    "e" -> Tile(x + 2, y)
                    "w" -> Tile(x - 2, y)
                    "ne" -> Tile(x + 1, y + 1)
                    "se" -> Tile(x + 1, y - 1)
                    "nw" -> Tile(x - 1, y + 1)
                    "sw" -> Tile(x - 1, y - 1)
                    else -> throw IllegalArgumentException("wrong direction $direction")
                }
            }
        }

        val blackTiles = mutableSetOf<Tile>()

        fun flip(tile: Tile) = blackTiles.remove(tile) || blackTiles.add(tile)

        input.forEach { line ->
            val seq = sequence {
                var idx = 0
                while (idx <= line.lastIndex) {
                    val d = when(val c1 = line[idx++]) {
                        'e', 'w' -> "$c1"
                        else -> "$c1${line[idx++]}"
                    }
                    yield(d)
                }
            }

            flip(seq.fold(Tile(0, 0)) { acc, s -> acc.neighbor(s) })
        }

        fun neighbors(tile: Tile) = listOf("e", "w", "ne", "se", "nw", "sw").map { tile.neighbor(it) }

        var currentBlack = blackTiles.toSet()

        var day = 0
        repeat(100) {
            val candidates = buildSet {
                currentBlack.forEach { tile ->
                    addAll(neighbors(tile))
                }
            }

            val newBlack = candidates.filter { c ->
                val isBlack = currentBlack.contains(c)
                val blackNeighbors = neighbors(c).count { currentBlack.contains(it) }
                isBlack && blackNeighbors in 1..2 || !isBlack && blackNeighbors == 2
            }

            currentBlack = newBlack.toSet()
            day++
//            println("Day $day: ${currentBlack.size}")

        }

        return currentBlack.size
    }

}

