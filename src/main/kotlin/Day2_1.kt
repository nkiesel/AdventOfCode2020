import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines


@ExperimentalPathApi
fun main() {
    class Entry(line: String) {
        val range: IntRange
        val c: Char
        val password: String
        init {
            val m = Regex("(\\d+)-(\\d+) (\\w): (\\w+)").matchEntire(line)!!.groupValues
            range = m[1].toInt()..m[2].toInt()
            c = m[3][0]
            password = m[4]
        }
    }

    val input = Path("input2").readLines().map { Entry(it) }

    val valid = input.map { entry ->
        val count = entry.password.toCharArray().count { it == entry.c }
        if (count in entry.range) 1 else 0
    }.sum()

    println(valid)
}
