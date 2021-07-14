import kotlin.test.assertEquals
import org.junit.jupiter.api.Test


class Day23 {
    @Test
    fun test() {
        val input = "624397158"
        val test = "389125467"
        assertEquals("92658374", one(test, 10))
        assertEquals("67384529", one(test, 100))
        assertEquals("74698532", one(input, 100))
    }

    private fun one(input: String, rounds: Int): String {
        val circle = ArrayDeque(input.toCharArray().map { (it - '0') })
        fun idx(i: Int) = i % circle.size
        var ci: Int = 0
        fun right(i: Int = 1) = idx(ci + i)
        repeat(rounds) {
            println("cups: ${circle.mapIndexed { i, c -> if (i == ci) "($c)" else "$c" }.joinToString(" ")}")
            val currentCup = circle[ci]
            val pickUps = (1..3).map { circle[right(it)] }
            println("pick up: $pickUps")
            var substract = 1
            var destinationCup = pickUps.first()
            while (destinationCup in pickUps) destinationCup = (currentCup - ++substract + circle.size) % circle.size + 1
            println("destination: $destinationCup\n")
            circle.removeAll(pickUps)
            var di = circle.indexOf(destinationCup)
            pickUps.forEach { cup -> circle.add(idx(di + 1), cup); di = circle.indexOf(cup) }
            ci = idx(circle.indexOf(currentCup) + 1)
        }
        ci = circle.indexOf(1)
        return (1 until circle.size).joinToString("") { circle[idx(ci + it)].toString() }
    }

}

