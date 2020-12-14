import java.lang.IllegalStateException
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test


@ExperimentalPathApi
class Day12 {
    @Test
    fun test() {
        val input = Path("input/12").readLines()
        val test = """
            F10
            N3
            F7
            R90
            F11
        """.trimIndent().lines()
        assertEquals(25, one(test))
        assertEquals(2057, one(input))
        assertEquals(286, two(test))
        assertEquals(71504, two(input))
    }

    private fun one(input: List<String>): Int {
        var x = 0
        var y = 0
        var a = 90
        input.map { line -> line[0] to line.drop(1).toInt() }.forEach { (direction, amount) ->
            when (direction) {
                'N' -> y += amount
                'S' -> y -= amount
                'E' -> x += amount
                'W' -> x -= amount
                'R' -> a += amount
                'L' -> a -= amount
                'F' -> when (a % 360) {
                    0 -> y += amount
                    90 -> x += amount
                    180 -> y -= amount
                    270 -> x -= amount
                }
            }
        }
        return abs(x) + abs(y)
    }

    data class Point(var x: Int, var y: Int) {
        fun move(dx: Int, dy: Int) {
            x += dx
            y += dy
        }

        fun move(direction: Point, amount: Int) = move(direction.x * amount, direction.y * amount)


        fun rotateRight(amount: Int) {
            assert(amount >= 0 && amount % 90 == 0) { "amount: $amount" }

            fun rotate90right() {
                x = y.also { y = -x }
            }

            repeat((amount % 360) / 90) { rotate90right() }
        }

        fun rotateLeft(amount: Int) = rotateRight(360 - amount)

        fun manhattanDistance() = abs(x) + abs(y)
    }

    private fun two(input: List<String>): Int {
        val ship = Point(0, 0)
        val waypoint = Point(10, 1)
        input.map { line -> line[0] to line.drop(1).toInt() }.forEach { (direction, amount) ->
            when (direction) {
                'N' -> waypoint.move(0, amount)
                'S' -> waypoint.move(0, -amount)
                'E' -> waypoint.move(amount, 0)
                'W' -> waypoint.move(-amount, 0)
                'R' -> waypoint.rotateRight(amount)
                'L' -> waypoint.rotateLeft(amount)
                'F' -> ship.move(waypoint, amount)
            }
        }
        return ship.manhattanDistance()
    }

}
