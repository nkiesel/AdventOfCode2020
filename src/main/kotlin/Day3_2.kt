import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines


@ExperimentalPathApi
fun main() {
    val input = Path("input3").readLines()

    fun slope(columnIncrement: Int, rowIncrement: Int): Int {
        var column = 0
        var row = 0
        var trees = 0
        while (true) {
            row += rowIncrement
            if (row >= input.size) {
                return trees
            }
            column = (column + columnIncrement) % input[row].length
            if (input[row][column] == '#') trees++
        }
    }

    println(slope(1, 1) * slope(3, 1) * slope(5, 1) * slope(7, 1) * slope(1, 2))
}
