import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines


@ExperimentalPathApi
fun main() {
    val input = Path("input3").readLines()

    var column = 0
    var row = 0
    val columnIncrement = 3
    val rowIncrement = 1
    var trees = 0

    while (true) {
        row += rowIncrement
        if (row >= input.size) {
            break
        }
        column = (column + columnIncrement) % input[row].length
        if (input[row][column] == '#') trees++
    }

    println(trees)
}
