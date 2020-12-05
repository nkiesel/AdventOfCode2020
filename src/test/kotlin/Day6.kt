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
        assertEquals(0, one(input))
        assertEquals(0, two(input))
    }

    private fun one(input: List<String>): Int {
        TODO("Not yet implemented")
    }

    private fun two(input: List<String>): Int {
        TODO("Not yet implemented")
    }
}
