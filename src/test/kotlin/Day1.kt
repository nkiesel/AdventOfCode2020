import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

@ExperimentalPathApi
class Day1 {
    @Test
    fun test() {
        assertEquals(970816, one())
        assertEquals(96047280, two())
    }

    private fun one(): Int {
        val input = Path("input/1").readLines().map { it.toInt() }

        input.forEachIndexed { index1, line1 ->
            for (line2 in input.drop(index1 + 1)) {
                if (line1 + line2 == 2020) {
                    return line1 * line2
                }
            }
        }
        return 0
    }

    private fun two(): Int {
        val input = Path("input/1").readLines().map { it.toInt() }

        input.forEachIndexed { index1, line1 ->
            input.drop(index1 + 1).forEachIndexed { index2, line2 ->
                input.drop(index2 + 1).forEachIndexed { _, line3 ->
                    if (line1 + line2 + line3 == 2020) {
                        return line1 * line2 * line3
                    }
                }
            }
        }
        return 0
    }
}
