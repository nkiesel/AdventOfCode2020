import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

@ExperimentalPathApi
class Day6 {
    @Test
    fun test() {
        val input = Path("input/6").readLines()
        assertEquals(6297, oneA(input))
        assertEquals(6297, one(input))
        assertEquals(3158, two(input))
    }

    private fun one(input: List<String>): Int {
        val group = mutableSetOf<Char>()
        var total = 0
        input.forEach { line ->
            if (line.isEmpty()) {
                total += group.size
                group.clear()
            } else {
                group += line.toSet()
            }
        }
        return total + group.size
    }

    private fun oneA(input: List<String>): Int {
        var group: Set<Char>? = null

        var total = 0
        fun count(): Set<Char>? { total += group?.size ?: 0; return null }
        input.forEach { line ->
            group = when {
                line.isEmpty() -> count()
                group == null -> line.toSet()
                else -> group!! union line.toSet()
            }
        }
        count()
        return total
    }

    private fun two(input: List<String>): Int {
        var group: Set<Char>? = null
        var total = 0
        input.forEach { line ->
            group = when {
                line.isEmpty() -> {
                    total += group?.size ?: 0; null
                }
                group == null -> line.toSet()
                else -> group!! intersect line.toSet()
            }
        }
        return total + (group?.size ?: 0)
    }
}
