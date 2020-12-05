import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

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
        return p.count { Passport(mandatory, it).isValid() }
    }

    private fun two(input: List<String>): Int {
        val passports = buildList {
            var current = ""
            input.forEach { line ->
                current = when {
                    line.isEmpty() -> "".also { if (current.isNotEmpty()) add(current) }
                    current.isEmpty() -> line
                    else -> "$current $line"
                }
            }
            if (current.isNotEmpty()) add(current)
        }

        // less "magic" but more code...
        val passports2 = buildList {
            val i = input.iterator()
            while (i.hasNext()) {
                val current = buildList {
                    while (i.hasNext()) {
                        val line = i.next()
                        if (line.isEmpty()) break
                        add(line)
                    }
                }.joinToString(" ")
                if (current.isNotEmpty()) add(current)
            }
        }

        class Field(val key: String, val constraint: (String) -> Boolean)

        val mandatory = listOf(
            Field("byr") { Regex("\\d{4}").matches(it) && it.toInt() in 1920..2002 },
            Field("iyr") { Regex("\\d{4}").matches(it) && it.toInt() in 2010..2020 },
            Field("eyr") { Regex("\\d{4}").matches(it) && it.toInt() in 2020..2030 },
            Field("hgt") { Regex("(\\d+)(cm|in)").matchEntire(it)?.destructured?.let { (height, unit) ->
                when (unit) {
                    "cm" -> height.toInt() in 150..193
                    "in" -> height.toInt() in 59..76
                    else -> false
                } } ?: false },
            Field("hcl") { Regex("#[0-9a-f]{6}").matches(it) },
            Field("ecl") { Regex("amb|blu|brn|gry|grn|hzl|oth").matches(it) },
            Field("pid") { Regex("\\d{9}").matches(it) },
        )

        class Passport(s: String) {
            val fields = s.split(" ").flatMap { it.split(":", limit=2).zipWithNext() }.toMap()
            // less "magic"...
            // val fields = s.split(" ").map { val (k, v) = it.split(":", limit=2); k to v }.toMap()
            // val fields = s.split(" ").map { it.split(":", limit=2).let { (k, v) -> k to v } }.toMap()

            fun isValid() = mandatory.all { m -> fields[m.key]?.let { m.constraint(it) } ?: false }
        }

        println(passports.count { Passport(it).isValid() })
        return passports2.count { Passport(it).isValid() }
    }
}
