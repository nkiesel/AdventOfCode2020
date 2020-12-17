import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test


@ExperimentalStdlibApi
@ExperimentalPathApi
class Day17 {
    @Test
    fun test() {
        val input = Path("input/17").readLines()
        val test = """
            .#.
            ..#
            ###
        """.trimIndent().lines()
        assertEquals(112, one(test))
        assertEquals(336, one(input))
        assertEquals(848, two(test))
        assertEquals(2620, two(input))
    }

    private fun one(input: List<String>): Int {
        data class Cube(val x: Int, val y: Int, val z: Int)

        var active = input.mapIndexedNotNull { row, line -> line.mapIndexedNotNull { col, c -> if (c == '#') Cube(col, row, 0) else null } }
            .flatten().toSet()

        repeat(6) {
            val candidates = buildSet {
                active.forEach { c ->
                    for (dx in -1..1) {
                        for (dy in -1..1) {
                            for (dz in -1..1) {
                                add(Cube(c.x + dx, c.y + dy, c.z + dz))
                            }
                        }
                    }
                }
            }
            val new = buildSet {
                candidates.forEach { c ->
                    var neighbors = 0
                    for (dx in -1..1) {
                        for (dy in -1..1) {
                            for (dz in -1..1) {
                                val n = Cube(c.x + dx, c.y + dy, c.z + dz)
                                if (n != c && active.contains(n)) {
                                    neighbors++
                                }
                            }
                        }
                    }
                    when(neighbors) {
                        3 -> add(c)
                        2 -> if (active.contains(c)) add(c)
                    }
                }
            }
            active = new
        }
        return active.size
    }

    private fun two(input: List<String>): Int {
        data class Cube(val x: Int, val y: Int, val z: Int, val w: Int)

        var active = input.mapIndexedNotNull { row, line -> line.mapIndexedNotNull { col, c -> if (c == '#') Cube(col, row, 0, 0) else null } }
            .flatten().toSet()

        repeat(6) {
            val candidates = buildSet {
                active.forEach { c ->
                    for (dx in -1..1) {
                        for (dy in -1..1) {
                            for (dz in -1..1) {
                                for (dw in -1..1) {
                                    add(Cube(c.x + dx, c.y + dy, c.z + dz, c.w + dw))
                                }
                            }
                        }
                    }
                }
            }
            val new = buildSet {
                candidates.forEach { c ->
                    var neighbors = 0
                    for (dx in -1..1) {
                        for (dy in -1..1) {
                            for (dz in -1..1) {
                                for (dw in -1..1) {
                                    val n = Cube(c.x + dx, c.y + dy, c.z + dz, c.w + dw)
                                    if (n != c && active.contains(n)) {
                                        neighbors++
                                    }
                                }
                            }
                        }
                    }
                    when (neighbors) {
                        3 -> add(c)
                        2 -> if (active.contains(c)) add(c)
                    }
                }
            }
            active = new
        }
        return active.size
    }

}
