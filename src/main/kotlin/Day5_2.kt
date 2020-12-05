import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines


@ExperimentalStdlibApi
@ExperimentalPathApi
fun main() {
    val input = Path("input5").readLines()

    fun seatId(code: String): Int {
        return code.toCharArray().fold(0) { acc, c -> acc * 2 + if (c == 'R' || c == 'B') 1 else 0 }
    }

    println(input.map { seatId(it) }.sorted().zipWithNext().find { it.second != it.first + 1 }!!.first + 1)
}
