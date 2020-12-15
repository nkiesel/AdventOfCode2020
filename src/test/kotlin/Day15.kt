import kotlin.io.path.ExperimentalPathApi
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test


@ExperimentalStdlibApi
@ExperimentalPathApi
class Day15 {
    @Test
    fun test() {
        assertEquals(436, one("0,3,6"))
        assertEquals(1, one("1,3,2"))
        assertEquals(10, one("2,1,3"))
        assertEquals(27, one("1,2,3"))
        assertEquals(78, one("2,3,1"))
        assertEquals(438, one("3,2,1"))
        assertEquals(1836, one("3,1,2"))
        assertEquals(492, one("1,20,8,12,0,14"))
        assertEquals(175594, two("0,3,6"))
        assertEquals(2578, two("1,3,2"))
        assertEquals(63644, two("1,20,8,12,0,14"))
    }

    private fun one(input: String): Int {
        val list = input.split(",").map { it.toInt() }.toMutableList()
        while (list.size < 2020) {
            val last = list.last()
            val prev = list.mapIndexedNotNull { index, i -> if (i == last) index else null }.takeLast(2)
            list.add(if (prev.size == 2) prev[1] - prev[0] else 0)
        }
        return list.last()
    }

    private fun two(input: String): Int {
        val start = input.split(",").map { it.toInt() }
        val indexOf = start.mapIndexed { index, num -> num to index }.toMap().toMutableMap()
        val prevIndexOf = mutableMapOf<Int, Int>()
        var number = start.last()
        for (index in start.size until 30000000) {
            number = when (val i = indexOf[number]) {
                null -> 0
                else -> when (val p = prevIndexOf[number]) {
                    null -> 0
                    else -> i - p
                }
            }
            indexOf[number]?.let { prevIndexOf[number] = it }
            indexOf[number] = index
        }
        return number
    }

    /* Notes
       - Obviously, `one` could be implemented using `two` with 2020 as limit, but I kept `one
         as I had it initially because it was "fast enough".
       - It took me some trial and error to get the correct logic for the index bookkeeping. `two`
         is still not really fast, but the whole test takes less than 25 seconds on my machine which
         means I stop optimizing.
     */
}
