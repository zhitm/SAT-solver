import kotlin.math.abs

class BooleanFormula() {
    var startClauses: MutableList<Clause> = mutableListOf()
    var varCnt: Int = 0
    var clauseCnt: Int = 0
    var variables = arrayOfNulls<Boolean>(varCnt)
    var canBeSolved = true
    var isSolved = false
    var unknownVariablesLeft = varCnt;
    var clauses: MutableList<Clause> = mutableListOf()
    var emptyClause: Clause? = null
    var lastLevel: MutableList<Clause> = mutableListOf()
    var newLastLevel: MutableList<Clause> = mutableListOf()


    fun setVarsCnt(cnt: Int) {
        varCnt = cnt
        variables = arrayOfNulls<Boolean>(varCnt)
        unknownVariablesLeft = varCnt
    }

    fun isEmpty(): Boolean {
        return (clauses.isEmpty() && lastLevel.isEmpty() && newLastLevel.isEmpty())
    }

    fun addClause(clause: Clause) {
        if (clause.length == 0) {
            canBeSolved = false
            emptyClause = clause
        } else {
            if (!hasClause(clause)) {
                clauses.add(clause)
                clauseCnt++
                if (clause.length == 1) {
                    val literal: Int = clause.varArray[0]
                    deleteAllUsesOfVariable(abs(literal), literal > 0, clauses)
                }
            }
        }
    }

    private fun hasClause(clause: Clause): Boolean {
        for (cl in clauses) {
            if (cl.varArray == clause.varArray) return true
        }
        return false
    }

    fun printState() {
//        println(".....")
//        for (el in startClauses) print(el.varArray)
//        println()
        for (el in clauses) print(el.varArray)
        println()
        for (el in lastLevel) print(el.varArray)
        println()
        for (el in newLastLevel) print(el.varArray)
        println()
    }

    private fun deleteClauseOrDeleteVariable(
        variable: Int,
        value: Boolean,
        clause: Clause,
        list: MutableList<Clause>
    ): Boolean {
        if (variable in clause.varArray) {
            if (value) {
                if (list == clauses)
                    clauseCnt--
                return false
            } else {
                clause.varArray.remove(variable)
                clause.length--
                if (clause.isEmpty()) {
                    if (list == clauses)
                        clauseCnt--
                    canBeSolved = false
                    return false
                }
            }
        } else if (-variable in clause.varArray) {
            if (value) {
                clause.varArray.remove(-variable)
                clause.length--
                if (clause.isEmpty()) {
                    if (list == clauses)
                        clauseCnt--
                    canBeSolved = false
                    return false
                }
            } else {
                if (list == clauses)
                    clauseCnt--
                return false
            }
        }
        return true
    }

    fun deleteAllUsesOfVariable(variable: Int, value: Boolean, list: MutableList<Clause>) {
        if (variables[variable - 1] == null)
            unknownVariablesLeft--
        variables[variable - 1] = value

        when (list) {
            clauses -> clauses =
                list.filter { deleteClauseOrDeleteVariable(variable, value, it, list) } as MutableList<Clause>
            lastLevel -> lastLevel =
                list.filter { deleteClauseOrDeleteVariable(variable, value, it, list) } as MutableList<Clause>
            newLastLevel -> newLastLevel =
                list.filter { deleteClauseOrDeleteVariable(variable, value, it, list) } as MutableList<Clause>
        }
        for (clause in list) {
            if (!canBeSolved) return
            if (unknownVariablesLeft == 0 || isEmpty()) {
                isSolved = true
                return
            }
            if (clause.isLiteral()) {
                deleteAllUsesOfVariable(abs(clause.varArray[0]), clause.varArray[0] > 0, clauses)
                deleteAllUsesOfVariable(abs(clause.varArray[0]), clause.varArray[0] > 0, lastLevel)
                deleteAllUsesOfVariable(abs(clause.varArray[0]), clause.varArray[0] > 0, newLastLevel)
            }
        }
    }
}
