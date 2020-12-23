import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test


@ExperimentalStdlibApi
@ExperimentalPathApi
class Day21 {
    @Test
    fun test() {
        val input = Path("input/21").readLines()
        val test = """
            mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
            trh fvjkl sbzzf mxmxvkd (contains dairy)
            sqjhc fvjkl (contains soy)
            sqjhc mxmxvkd sbzzf (contains fish)
        """.trimIndent().lines()
        val (t1, t2) = one(test)
        assertEquals(5, t1)
        assertEquals("mxmxvkd,sqjhc,fvjkl", t2)
        val (r1, r2) = one(input)
        assertEquals(2542, r1)
        assertEquals("hkflr,ctmcqjf,bfrq,srxphcm,snmxl,zvx,bd,mqvk", r2)
    }

    private fun one(input: List<String>): Pair<Int, String> {
        val candidates = mutableMapOf<String, Set<String>>()
        val counts = mutableMapOf<String, Int>()
        input.forEach { line ->
            val (i, a) = line.split(" (contains ")
            val ingredients = i.split(" ").toSet()
            ingredients.groupingBy { it }.eachCountTo(counts)
            val allergens = a.dropLast(1).split(", ")
            allergens.forEach { allergen ->
                candidates.merge(allergen, ingredients) { current, new -> current intersect new }
            }
        }

        val contains = mutableMapOf<String, String>()
        val ca = candidates.mapValues { it.value.toMutableSet() }.toMutableMap()

        while (true) {
            val c = ca.entries.find { it.value.size == 1} ?: break
            val ingredient = c.value.first()
            counts.remove(ingredient)
            ca.remove(c.key)
            contains[ingredient] = c.key
            ca.forEach { it.value.remove(ingredient) }
        }

        return Pair(
            counts.values.sumOf { it },
            contains.entries.sortedBy { it.value }.joinToString(",") { it.key })
    }

}

