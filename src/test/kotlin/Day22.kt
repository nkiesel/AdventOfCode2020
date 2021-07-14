import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test


class Day22 {
    @Test
    fun test() {
        val input = Path("input/22").readLines()
        val test = """
            Player 1:
            9
            2
            6
            3
            1

            Player 2:
            5
            8
            4
            7
            10
        """.trimIndent().lines()
        assertEquals(306, one(test))
        assertEquals(30138, one(input))
        assertEquals(291, two(test))
        assertEquals(34541, two(input))
        val test2 = """
            Player 1:
            43
            19

            Player 2:
            2
            29
            14
        """.trimIndent().lines()
        two(test2)
    }

    private fun one(input: List<String>): Int {
        data class Player(val id: Int, val cards: ArrayDeque<Int>)

        val players = buildList {
            input.chunkedBy { it.isEmpty() }.forEach { p ->
                val id = Regex("Player (\\d+):").matchEntire(p.first())!!.groupValues[1].toInt()
                val cards = p.drop(1).map { it.toInt() }
                add(Player(id, ArrayDeque(cards)))
            }
        }

        while (players.all { it.cards.isNotEmpty() }) {
            val cards = players.map { it.cards.removeFirst() }
            if (cards[0] > cards[1]) {
                players[0].cards.addAll(cards)
            } else {
                players[1].cards.addAll(cards.reversed())
            }
        }

        return players.find { it.cards.isNotEmpty() }!!.cards.reversed().foldIndexed(0) { idx, acc, i -> acc + (idx + 1) * i }
    }

    private fun two(input: List<String>): Int {
        data class Player(val id: Int, val cards: ArrayDeque<Int>)

        val players = buildList {
            input.chunkedBy { it.isEmpty() }.forEach { p ->
                val id = Regex("Player (\\d+):").matchEntire(p.first())!!.groupValues[1].toInt()
                val cards = p.drop(1).map { it.toInt() }
                add(Player(id, ArrayDeque(cards)))
            }
        }

        fun play(p1: Player, p2: Player): Player {
            val seen = mutableSetOf<List<Int>>()
            while (p1.cards.isNotEmpty() && p2.cards.isNotEmpty()) {
                if (!seen.add(p1.cards.toList() + listOf(0) + p2.cards.toList())) return p1

                val c1 = p1.cards.removeFirst()
                val c2 = p2.cards.removeFirst()
                val winner = when {
                    p1.cards.size >= c1 && p2.cards.size >= c2 ->
                        play(
                            p1.copy(cards = ArrayDeque(p1.cards.take(c1))),
                            p2.copy(cards = ArrayDeque(p2.cards.take(c2))),
                        )
                    c1 > c2 -> p1
                    else -> p2
                }
                if (winner == p1) {
                    p1.cards.add(c1)
                    p1.cards.add(c2)
                } else {
                    p2.cards.add(c2)
                    p2.cards.add(c1)
                }
            }
            return if (p1.cards.isNotEmpty()) p1 else p2
        }

        val overallWinner = play(players[0], players[1])
        return overallWinner.cards.reversed().reduceIndexed { idx, acc, i -> acc + (idx + 1) * i }
    }

    /* Notes
        - overall simple, but `two` returns the wrong value!!!
     */
}

