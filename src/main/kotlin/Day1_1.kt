import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines

@ExperimentalPathApi
fun main() {
    val input = Path("input1").readLines().map { it.toInt() }

    input.forEachIndexed { index1, line1 ->
        for (line2 in input.drop(index1 + 1)) {
            if (line1 + line2 == 2020) {
                println(line1 * line2)
            }
        }
    }
}
