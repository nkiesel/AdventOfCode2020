import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import org.junit.jupiter.api.Test

@ExperimentalTime
@ExperimentalStdlibApi
@ExperimentalPathApi
class Day4 {
    @Test
    fun test() {
        val input = Path("input/4").readLines()
        assertEquals(230, one(input))
        assertEquals(156, two(input))
    }

    private fun one(input: List<String>): Int {
        val passports = input.chunkedBy { it.isBlank() }.map { l -> l.joinToString(" ") }

        class Passport(s: String) {
            val fields = s.split(" ").flatMap { it.split(":").zipWithNext() }.toMap()
            fun isValid(mandatory: Set<String>) = (mandatory - fields.keys).isEmpty()
        }

        val mandatory = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
        return passports.count { Passport(it).isValid(mandatory) }
    }

    private fun two(input: List<String>): Int {
        val passports = input.chunkedBy { it.isEmpty() }.map { l -> l.joinToString(" ") }

        class Rule(val key: String, val constraint: (String) -> Boolean)

        val mandatory = listOf(
            Rule("byr") { Regex("\\d{4}").matches(it) && it.toInt() in 1920..2002 },
            Rule("iyr") { Regex("\\d{4}").matches(it) && it.toInt() in 2010..2020 },
            Rule("eyr") { Regex("\\d{4}").matches(it) && it.toInt() in 2020..2030 },
            Rule("hgt") { Regex("(\\d+)(cm|in)").matchEntire(it)?.destructured?.let { (height, unit) ->
                when (unit) {
                    "cm" -> height.toInt() in 150..193
                    else -> height.toInt() in 59..76
                } } ?: false },
            Rule("hcl") { Regex("#[0-9a-f]{6}").matches(it) },
            Rule("ecl") { Regex("amb|blu|brn|gry|grn|hzl|oth").matches(it) },
            Rule("pid") { Regex("\\d{9}").matches(it) },
        )

        class Passport(s: String) {
            val fields = s.split(" ").flatMap { it.split(":").zipWithNext() }.toMap()
            fun isValid(mandatory: List<Rule>) = mandatory.all { m -> fields[m.key]?.let { m.constraint(it) } ?: false }
        }

        return passports.count { Passport(it).isValid(mandatory) }
    }
}

// "chunkedBy" was copied from https://github.com/edgars-supe/advent-of-code/blob/master/src/main/kotlin/lv/esupe/aoc/utils/ListUtil.kt#L59-L64
fun <T> List<T>.chunkedBy(selector: (T) -> Boolean): List<List<T>> =
    fold(mutableListOf(mutableListOf<T>())) { acc, item ->
        if (selector(item)) {
            acc.add(mutableListOf())
        } else {
            acc.last().add(item)
        }
        acc
    }
