import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines

@ExperimentalPathApi
fun main() {
    val input = Path("input1").readLines().map { it.toInt() }
    input.forEachIndexed { index1, line1 ->
        input.drop(index1 + 1).forEachIndexed { index2, line2 ->
            input.drop(index2 + 1).forEachIndexed { _, line3 ->
                if (line1 + line2 + line3 == 2020) {
                    println(line1 * line2 * line3)
                }
            }
        }
    }
}
