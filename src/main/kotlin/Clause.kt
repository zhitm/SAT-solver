import com.sun.org.apache.xpath.internal.operations.Variable
import kotlin.math.abs

class Clause(array: MutableList<Int>, val parent1: Clause? = null, val parent2: Clause? = null) {
    var varArray = array
    var length = array.size
    var value = false
    init {
        simplify()
        varArray.sortBy { abs(it) }
    }

    fun isEmpty(): Boolean {
        return length == 0
    }

    fun isLiteral(): Boolean {
        return length == 1
    }

    fun copy(): Clause {
        return Clause(varArray.toMutableList())
    }

    fun canBeResolute(clause: Clause, variable: Int): Boolean {
        return (-variable in clause.varArray)
    }

    fun resolute(clause: Clause, variable: Int): Clause {
        val arr: MutableList<Int> = mutableListOf()
        for (el in varArray) {
            if (el != variable && el !in arr) arr.add(el)
        }
        for (el in clause.varArray) {
            if (-el != variable && el !in arr) arr.add(el)
        }
        return Clause(arr, this, clause)
    }

    fun simplify() {
        for (el in varArray) {
            if (-el in varArray) {
                value = true
            }
//            for (i in 1 until varArray.count { it == el }) {
//                varArray.remove(el)
//                length--
//            }

        }
//        val set: Set<Int> = varArray.toSet()
//        varArray = set.toMutableList()
    }
}