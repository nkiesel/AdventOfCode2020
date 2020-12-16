import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test


@ExperimentalStdlibApi
@ExperimentalPathApi
class Day16 {
    @Test
    fun test() {
        val input = Path("input/16").readLines()
        assertEquals(26053, one(input))
        assertEquals(1515506256421L, two(input))
    }

    private fun one(input: List<String>): Int {
        val valid = mutableMapOf<String, List<IntRange>>()
        val otherTickets = mutableListOf<List<Int>>()
        var state = 0
        for (line in input) {
            when {
                line == "your ticket:" -> state = 1
                line == "nearby tickets:" -> state = 2
                line.isNotEmpty() -> when (state) {
                    0 -> {
                        val parts = line.split(": ")
                        valid[parts[0]] = Regex("""\d+-\d+""").findAll(parts[1])
                            .map { r -> r.groupValues[0].split("-").map { it.toInt() } }
                            .map { (s, e) -> s..e }
                            .toList()
                    }
                    2 -> otherTickets += line.split(",").map { it.toInt() }
                }
            }
        }
        fun invalid(list: List<Int>) = list.sumOf { n -> if (valid.values.flatten().none { n in it }) n else 0 }
        return otherTickets.sumOf { t -> invalid(t) }
    }

    private fun two(input: List<String>): Long {
        val fields = mutableMapOf<String, List<IntRange>>()
        val myTicket = mutableListOf<Int>()
        val otherTickets = mutableListOf<List<Int>>()
        var state = 0
        for (line in input) {
            when {
                line == "your ticket:" -> state = 1
                line == "nearby tickets:" -> state = 2
                line.isNotEmpty() -> when (state) {
                    0 -> {
                        val parts = line.split(": ")
                        fields[parts[0]] = Regex("""\d+-\d+""").findAll(parts[1])
                            .map { r -> r.groupValues[0].split("-").map { it.toInt() } }
                            .map { (s, e) -> s..e }
                            .toList()
                    }
                    1 -> myTicket += line.split(",").map { it.toInt() }
                    2 -> otherTickets += line.split(",").map { it.toInt() }
                }
            }
        }

        val validRanges = fields.values.flatten()
        val validTickets = otherTickets.filter { ticket -> ticket.all { n -> validRanges.any { n in it } } }

        val candidates = fields.map { it.key to mutableListOf<Int>() }.toMap()
        myTicket.indices.forEach { index ->
            val values = validTickets.map { it[index] }
            fields.entries.filter { (_, ranges) -> values.all { v -> ranges.any { v in it } } }.forEach { candidates[it.key]!!.add(index) }
        }

        val ticket = mutableMapOf<String, Int>()
        while (candidates.values.any { it.isNotEmpty() }) {
            val c = candidates.entries.find { it.value.size == 1 } ?: throw IllegalStateException("no candidate with single value")
            val index = c.value[0]
            ticket[c.key] = index
            candidates.values.forEach { it.remove(index) }
        }

        return ticket.entries.filter { it.key.startsWith("departure") }.fold(1L) { acc, t -> acc * myTicket[t.value] }
    }


}
