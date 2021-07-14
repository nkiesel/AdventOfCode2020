import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test


class Day19 {
    @Test
    fun test() {
        val input = Path("input/19").readLines()
        val test = """
            0: 4 1 5
            1: 2 3 | 3 2
            2: 4 4 | 5 5
            3: 4 5 | 5 4
            4: "a"
            5: "b"

            ababbb
            bababa
            abbbab
            aaabbb
            aaaabbb
        """.trimIndent().lines()
        assertEquals(2, one(test))
        assertEquals(176, one(input))
        val test2 = """
            42: 9 14 | 10 1
            9: 14 27 | 1 26
            10: 23 14 | 28 1
            1: "a"
            11: 42 31
            5: 1 14 | 15 1
            19: 14 1 | 14 14
            12: 24 14 | 19 1
            16: 15 1 | 14 14
            31: 14 17 | 1 13
            6: 14 14 | 1 14
            2: 1 24 | 14 4
            0: 8 11
            13: 14 3 | 1 12
            15: 1 | 14
            17: 14 2 | 1 7
            23: 25 1 | 22 14
            28: 16 1
            4: 1 1
            20: 14 14 | 1 15
            3: 5 14 | 16 1
            27: 1 6 | 14 18
            14: "b"
            21: 14 1 | 1 14
            25: 1 1 | 1 14
            22: 14 14
            8: 42
            26: 14 22 | 1 20
            18: 15 15
            7: 14 5 | 1 21
            24: 14 1

            abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
            bbabbbbaabaabba
            babbbbaabbbbbabbbbbbaabaaabaaa
            aaabbbbbbaaaabaababaabababbabaaabbababababaaa
            bbbbbbbaaaabbbbaaabbabaaa
            bbbababbbbaaaaaaaabbababaaababaabab
            ababaaaaaabaaab
            ababaaaaabbbaba
            baabbaaaabbaaaababbaababb
            abbbbabbbbaaaababbbbbbaaaababb
            aaaaabbaabaaaaababaa
            aaaabbaaaabbaaa
            aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
            babaaabbbaaabaababbaabababaaab
            aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba
        """.trimIndent().lines()
        assertEquals(3, one(test2))
        assertEquals(12, two(test2))
        assertEquals(352, two(input))
    }

    private fun one(input: List<String>): Int {
        var state = 0
        var re = mutableMapOf<Regex, String>()
        val data = mutableListOf<String>()
        input.forEach { line ->
            when(state) {
                0 -> if (line.isEmpty()) {
                    state = 1
                } else {
                    line.split(": ").let { (a, b) -> re[Regex("\\b$a\\b")] = b }
                }
                1 -> data.add(line)
            }
        }

        while (re.size > 1) {
            val r = re.entries.find { !it.value.contains(Regex("\\d")) }!!
            re.remove(r.key)
            re = re.entries.map { (k, v) -> k to v.replace(r.key, "(${r.value})") }.toMap().toMutableMap()
        }

        val rule0 = re.values.first().replace(Regex("[ \"]"), "").replace(Regex("\\((.)\\)"), "$1").toRegex()

        return data.count { d -> rule0.matches(d) }
    }

    private fun two(input: List<String>): Int {
        var state = 0
        var re = mutableMapOf<String, String>()
        val data = mutableListOf<String>()
        fun String.re() = Regex("\\b$this\\b")
        input.forEach { line ->
            when(state) {
                0 -> if (line.isEmpty()) {
                    state = 1
                } else {
                    line.split(": ").let { (a, b) -> re[a] = b }
                }
                1 -> data.add(line)
            }
        }

        val special = "000"
        re["8"] = "42 $special"
        re["11"] = "42 $special 31"
        re.remove("0")

        while (re.size > 2) {
            val r = re.entries.find { !it.value.contains(Regex("\\d")) }!!
            re.remove(r.key)
            re = re.entries.map { (k, v) -> k to v.replace(r.key.re(), "(${r.value})") }.toMap().toMutableMap()
        }

        fun cleanup(s: String) = s.replace(Regex("[ \"]"), "").replace(Regex("\\((.)\\)"), "$1")

        val rule8 = cleanup(re["8"]!!.replace(special, ""))
        val rule11 = re["11"]!!.split(special).map { cleanup(it) }

        val rules = (1..10).map { "{$it}" }.map {rep ->
            "(${rule8})+(${rule11[0]})$rep(${rule11[1]})$rep".toRegex()
        }

        return data.count { d -> rules.any { it.matches(d) } }
    }

    /* Notes
      - the tricky part (for me) was to make sure that the 2 halfs of rule 11 match the same number of times. I "brute forced"
        this by just trying up to 10 repetitions.
     */

}
