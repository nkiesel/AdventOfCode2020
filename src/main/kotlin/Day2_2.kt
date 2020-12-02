import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines


@ExperimentalPathApi
fun main() {
    class Entry(line: String) {
        val positions: List<Int>
        val c: Char
        val password: String
        init {
            val m = Regex("(\\d+)-(\\d+) (\\w): (\\w+)").matchEntire(line)!!.groupValues
            positions = listOf(m[1].toInt() - 1, m[2].toInt() - 1)
            c = m[3][0]
            password = m[4]
        }
    }

    val input = Path("input2").readLines().map { Entry(it) }

    val valid = input.map { entry ->
        val count = entry.positions.count { entry.password[it] == entry.c }
        if (count == 1) 1 else 0
    }.sum()

    println(valid)
}
