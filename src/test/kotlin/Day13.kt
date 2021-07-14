import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test


class Day13 {
    @Test
    fun test() {
        val input = Path("input/13").readLines()
        val test = """
            939
            7,13,x,x,59,x,31,19
        """.trimIndent().lines()
        val test2 = """
            939
            7,13
        """.trimIndent().lines()
        val test3 = """
            939
            17,x,13,19
        """.trimIndent().lines()
        val test4 = """
            1
            67,7,59,61
        """.trimIndent().lines()
        val test5 = """
            1
            67,x,7,59,61
        """.trimIndent().lines()
        val test6 = """
            1
            67,7,x,59,61
        """.trimIndent().lines()
        val test7 = """
            1
            1789,37,47,1889
        """.trimIndent().lines()
        assertEquals(295, one(test))
        assertEquals(2092, one(input))
        assertEquals(77L, two(test2))
        assertEquals(3417L, two(test3))
        assertEquals(754018L, two(test4))
        assertEquals(779210L, two(test5))
        assertEquals(1261476L, two(test6))
        assertEquals(1202161486L, two(test7))
        assertEquals(1068781L, two(test))
        assertEquals(702970661767766L, two(input))
    }

    private fun one(input: List<String>): Int {
        val earliest = input[0].toInt()
        val available = input[1].split(",").filter{ it != "x" }.map { it.toInt() }
        fun minWait(bus: Int) = if (earliest % bus == 0) 0 else bus - earliest % bus
        val bus = available.minByOrNull { minWait(it) }!!
        return minWait(bus) * bus
    }

    private fun two(input: List<String>): Long {
        data class Bus(val number: Long, val index: Long) {
            fun onSchedule(time: Long) = (time + index) % number == 0L
        }
        val available = input[1].split(",")
            .mapIndexedNotNull { index, s -> if (s == "x") null else Bus(s.toLong(), index.toLong()) }
            .sortedByDescending { bus -> bus.number }
            .toMutableList()
        val first = available.removeFirst()
        var t = first.number - first.index
        var increment = first.number
        for (bus in available) {
            while (!bus.onSchedule(t)) {
                t += increment
            }
            increment *= bus.number
        }
        return t
    }

    /* Notes
        - one was simple, but I _really_ struggled with finding an efficient solution for two. I first had a simple
          "test increasing number for all busses" which worked but did not terminate for the real input within 10
          minutes.  I then thought I could find a formula which would compute the value, but that failed as well.
          I finally came up with the idea of incrementally add more busses, and compute the increment in a way that
          the already covered busses stay acceptable.  I found the correct value the next increment by computing
          possible further results for a simple test case.  I honestly cannot really say why this works or if there
          is not a better approach.  I blew through my self-imposed "1 hour" timeline on this, but it was Sunday...
     */
}
