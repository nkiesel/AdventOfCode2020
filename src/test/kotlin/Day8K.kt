import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Day8K {
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
        assertEquals(5, one(test))
        assertEquals(1930, one(input))
        assertEquals(1688, two(input))
    }

    data class State(var index: Int, var register: Int)

    sealed class Instruction(val deltaIndex: Int, val deltaRegister: Int) {
        fun execute(state: State) {
            state.index += deltaIndex
            state.register += deltaRegister
        }
    }

    class Jmp(val value: Int) : Instruction(value, 0)
    class Nop(val value: Int) : Instruction(1, 0)
    class Acc(val value: Int) : Instruction(1, value)

    private fun Instruction(line: String): Instruction {
        val (op, va) = line.split(" ")
        val value = va.toInt()
        return when(op) {
            "nop" -> Nop(value)
            "jmp" -> Jmp(value)
            "acc" -> Acc(value)
            else -> throw IllegalArgumentException("Invalid operation $op")
        }
    }

    fun execute(program: List<Instruction>): State {
        val state = State(0, 0)
        val executed = mutableSetOf<Int>()
        while (state.index in program.indices && executed.add(state.index)) {
            program[state.index].execute(state)
        }
        return state
    }


    private fun one(input: List<String>): Int {
        val program = input.map { Instruction(it) }
        val finalState = execute(program)
        return finalState.register
    }

    private fun mutations(program: List<Instruction>) = sequence<List<Instruction>> {
        program.forEachIndexed { index, instruction ->
            when(instruction) {
                is Nop -> yield(program.toMutableList().apply { this[index] = Jmp(instruction.value) })
                is Jmp -> yield(program.toMutableList().apply { this[index] = Nop(instruction.value) })
                else -> {}
            }
        }
    }

    private fun two(input: List<String>): Int {
        val program = input.map { Instruction(it) }
        val finalState = mutations(program).map { execute(it) }.first { it.index !in program.indices }
        return finalState.register
    }

    /*
    Notes:
     - one was pretty straightforward
     - for two I first tried to patch the "final instruction". Then I realized that a "jmp" into a final range consisting of
       just "acc" or "nop" or forward "jmp" would also terminate and tried to see if modifying an instruction would end up in
       that range.  But that failed as well because that jump into the final range could actually happen from another "jmp"
       which was not yet executed.  Thus the "brute force" approach to modify an instruction at a time and find the first
       modified program which does terminate.
     - this variation was created after watching the JetBrains video which used a similar approach:
         - factory function for converting lines into instructions
         - sealed class hierarchy for instructions
         - sequence for generating mutations
     */

}
