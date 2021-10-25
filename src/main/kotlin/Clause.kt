import kotlin.math.abs

class Clause(array: MutableList<Int>, val parent1: Clause? = null, val parent2: Clause? = null) {
    var varArray = array
    var length = array.size
    var hasProposalLiterals = false
    init {
        checkForProposalLiterals()
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

    private fun checkForProposalLiterals() {
        for (el in varArray) {
            if (-el in varArray) {
                hasProposalLiterals = true
            }
        }
    }
}