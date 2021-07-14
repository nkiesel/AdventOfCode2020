import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Day7 {
    @Test
    fun test() {
        val input = Path("input/7").readLines()
        assertEquals(238, one(input))
        assertEquals(82930, two(input))
    }

    private fun one(input: List<String>): Int {
        val map = input.associate { line ->
            Regex("""^(.+?) bags contain""").find(line)!!.groupValues[1] to
                    Regex("""\d+ (.+?) bags?[,.]""").findAll(line).map { it.groupValues[1] }.toSet()
        }

        val total = mutableSetOf<String>()
        var lookingFor = setOf("shiny gold")
        while (true) {
            val containers = map.filter { (it.value intersect lookingFor).isNotEmpty() }.map { it.key }.toSet()
            if (containers.isEmpty()) return total.size
            total.addAll(containers)
            lookingFor = containers
        }
    }

    private fun two(input: List<String>): Int {
        val bag = Regex("""^(.+?) bags contain""")
        val contains = Regex("""(\d+) (.+?) bags?[,.]""")
        val map = input.associate { line ->
            bag.find(line)!!.groupValues[1] to
                    contains.findAll(line).map { it.groupValues[1].toInt() to it.groupValues[2] }
        }

        fun containsBags(bag: String): Int = map[bag]!!.sumOf { it.first * (containsBags(it.second) + 1) }

        return containsBags("shiny gold")
    }

    /*
     Notes:
         - we must use non-greedy patterns for bag names!
         - I initially had a wrong result for `one` because I did not think about duplicates and added up the size of the individual results.
         - I initially had wrong result for `two` because I used `sumOf { it.first * containsBags(it.second) }` which always computes to `0`.
          It took me a while to realize that "off by one" error.
         - This would all fail if "contains" between bags would produce a cycle. For real code, we should rather use a "DAG" library and detect
          such cycles.
         - The recursion in `two` is terminating because `sumOf` for empty lists returns `0`.
         - The `!!` in `two` could be avoided by using `map[bag]?.sumOf { ... } ?: 0` but we trust the input file here for sake of brevity
         - I thought about using a single regex for `bag` and `contains` but could not find an elegant way to do this.
         - In production code, I would move the `Regex` definitions out of the lambda and perhaps even move them into the
          companion object because otherwise they are recreated for every iteration (or every method invocation).
     */
}
