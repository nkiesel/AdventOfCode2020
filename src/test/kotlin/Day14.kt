import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.pow
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test


@ExperimentalStdlibApi
@ExperimentalPathApi
class Day14 {
    @Test
    fun test() {
        val input = Path("input/14").readLines()
        val test = """
            mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
            mem[8] = 11
            mem[7] = 101
            mem[8] = 0
        """.trimIndent().lines()
        assertEquals(165L, one(test))
        assertEquals(7477696999511L, one(input))
        val test2 = """
            mask = 000000000000000000000000000000X1001X
            mem[42] = 100
            mask = 00000000000000000000000000000000X0XX
            mem[26] = 1
        """.trimIndent().lines()
        assertEquals(208L, two(test2))
        assertEquals(3687727854171L, two(input))
    }

    private fun one(input: List<String>): Long {
        val mem = mutableMapOf<Int, Long>()
        var orMask = 0L
        var andMask = 0L
        fun masked(value: Long) = value or orMask and andMask
        input.forEach { line ->
            val r = Regex("""(mask|mem\[(\d+)\]) = (.+)""").matchEntire(line)!!.groupValues
            if (r[1] == "mask") {
                orMask = r[3].replace("X", "0").toLong(2)
                andMask = r[3].replace("X", "1").toLong(2)
            } else {
                mem[r[2].toInt()] = masked(r[3].toLong())
            }
        }
        return mem.values.sum()
    }

    private fun two(input: List<String>): Long {
        val mem = mutableMapOf<Long, Long>()
        var orMask = 0L
        var mask = ""
        var numX = 0
        var numPermutations = 0
        var indices = listOf<Int>()

        fun permute(value: Long): List<Long> {
            return buildList {
                for (i in 0..numPermutations) {
                    val bits = i.toString(2).padStart(numX, '0')
                    val v = value.toString(2).padStart(mask.length, '0').toCharArray()
                    indices.forEachIndexed { bi, vi -> v[vi] = bits[bi] }
                    add(v.joinToString("").toLong(2))
                }
            }
        }

        input.forEach { line ->
            val r = Regex("""(mask|mem\[(\d+)\]) = (.+)""").matchEntire(line)!!.groupValues
            if (r[1] == "mask") {
                mask = r[3]
                orMask = mask.replace("X", "0").toLong(2)
                numX = mask.count { it == 'X' }
                numPermutations = 2.toDouble().pow(numX).toInt()
                indices = mask.mapIndexedNotNull { index, c ->  if (c == 'X') index else null }
            } else {
                val value = r[3].toLong()
                permute(r[2].toLong()).forEach { mem[it or orMask] = value }
            }
        }
        return mem.values.sum()
    }

    /* Notes
       - First of all, I do not like bit manipulations very much.  However, the input is short enough that I can
         get away with my most likely inefficient approach.
       - I also really do not like that I have to initialize variables like `mask` and indices` although I know
         that the initial values will never be used.  There must be a better way to do this...
     */

}
