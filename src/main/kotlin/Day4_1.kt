import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines


@ExperimentalPathApi
fun main() {
    val input = Path("input4").readLines()

    class Passport(val mandatory: Set<String>, s: String) {
        val map = s.trim().split(Regex("\\s+")).map { p -> val (k, v) = p.split(":"); k to v }.toMap()

        fun isValid() = (mandatory - map.keys).isEmpty()
    }

    val p = mutableListOf<String>()
    p.add(input.reduce { acc, string ->
        if (string.isEmpty()) {
            p.add(acc); ""
        } else "$acc $string"
    })

    val mandatory = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    println(p.count { Passport(mandatory, it).isValid() })
}
