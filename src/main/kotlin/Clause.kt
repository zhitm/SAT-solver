import kotlin.math.abs

class Clause(array: MutableList<Int>, val parent1: Clause? = null, val parent2: Clause? = null) {
    var varArray = array
    var length = array.size

    init {
        varArray.sortBy { abs(it) }
        simplify()
    }

    fun isEmpty(): Boolean {
        return length == 0
    }

    fun isLiteral(): Boolean {
        return length == 1
    }

    fun copy() : Clause {
        return Clause(varArray.toMutableList())
    }

    fun canBeResolute(clause: Clause): Boolean {
        for (el in varArray) {
            if (-el in clause.varArray)
                return true
        }
        for (el in clause.varArray) {
            if (-el in varArray)
                return true
        }
        return false
    }

    fun resolute(clause: Clause): Clause {
        val arr: MutableList<Int> = mutableListOf()
        for (el in varArray) {
            if (-el !in clause.varArray && el !in arr)
                arr.add(el)
        }
        for (el in clause.varArray) {
            if (-el !in varArray && el !in arr)
                arr.add(el)
        }
        return Clause(arr, this, clause)
    }

    private fun simplify() {
        for (el in varArray) {
            if (-el in varArray) {
                varArray = mutableListOf()
                length = 0
                return
            }
            for (i in 1 until varArray.count { it == el }) {
                varArray.remove(el)
                length--
            }
        }
    }
}