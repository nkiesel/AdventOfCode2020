import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

@ExperimentalPathApi
class Day8 {
    @Test
    fun test() {
        val input = Path("input/8").readLines()
        val test = """
            nop +0
            acc +1
            jmp +4
            acc +3
            jmp -3
            acc -99
            acc +1
            jmp -4
            acc +6
        """.trimIndent().lines()
        assertEquals(1930, one(input))
        assertEquals(1688, two(input))
    }

    private fun one(input: List<String>): Int {
        val executedInstructions = mutableSetOf<Int>()
        var accumulator = 0
        var currentInstruction = 0
        while (true) {
            if (!executedInstructions.add(currentInstruction)) return accumulator
            val (instruction, argument) = input[currentInstruction].split(" ")
            if (instruction == "acc") accumulator += argument.toInt()
            currentInstruction += if (instruction == "jmp") argument.toInt() else 1
        }
    }

    private fun two(input: List<String>): Int {
        fun one(input: List<String>): Int? {
            val executedInstructions = mutableSetOf<Int>()
            var accumulator = 0
            var currentInstruction = 0
            while (currentInstruction != input.size) {
                if (!executedInstructions.add(currentInstruction)) return null
                val (instruction, argument) = input[currentInstruction].split(" ")
                if (instruction == "acc") accumulator += argument.toInt()
                currentInstruction += if (instruction == "jmp") argument.toInt() else 1
            }
            return accumulator
        }

        input.forEachIndexed { i, line ->
            val modified = when {
                line.startsWith("jmp") -> line.replace("jmp", "nop")
                line.startsWith("nop") -> line.replace("nop", "jmp")
                else -> null
            }
            if (modified != null) {
                val result = one(input.subList(0, i) + modified + input.subList(i + 1, input.size))
                if (result != null) return result
            }
        }
        return -1
    }

    /*
    Notes:
     - one was pretty straightforward
     - for two I first tried to patch the "final instruction". Then I realized that a "jmp" into a final range consisting of
       just "acc" or "nop" or forward "jmp" would also terminate and tried to see if modifying an instruction would end up in
       that range.  But that failed as well because that jump into the final range could actually happen from another "jmp"
       which was not yet executed.  Thus the "brute force" approach to modify an instruction at a time and find the first
       modified program which does terminate.
     */

}
