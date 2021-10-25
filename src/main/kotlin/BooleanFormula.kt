import java.util.*
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
    val stackOfKnownLiterals: Stack<Int> = Stack()

    fun setVarsCnt(cnt: Int) {
        varCnt = cnt
        variables = arrayOfNulls<Boolean>(varCnt)
        unknownVariablesLeft = varCnt
    }

    fun isEmpty(): Boolean {
        return (clauses.isEmpty())
    }

    fun addClause(clause: Clause) {
        clauses.add(clause)
        clauseCnt++
    }

    fun hasClause(clause: Clause): Boolean {
        for (cl in clauses) {
            if (cl.varArray.toSet() == clause.varArray.toSet()) return true
        }
        return false
    }

    fun hasClauseAtNewLevels(clause: Clause): Boolean {
        for (cl in lastLevel) {
            if (cl.varArray.toSet() == clause.varArray.toSet()) return true
        }
        for (cl in lastLevel) {
            if (cl.varArray.toSet() == clause.varArray.toSet()) return true
        }
        return false
    }

    fun printState() {
        println(".....")
        for (el in startClauses) print(el.varArray)
        println()
        for (el in clauses) print(el.varArray)
        println()
        for (el in lastLevel) print(el.varArray)
        println()
        for (el in newLastLevel) print(el.varArray)
        println()
    }

    private fun deleteClauseOrDeleteVariable(variable: Int, value: Boolean, clause: Clause): Boolean {
        if (variable in clause.varArray) {
            if (value) {
                clauseCnt--
                return false
            } else {
                clause.varArray.remove(variable)
                clause.length--
                if (clause.isEmpty()) {
                    emptyClause = clause
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
                    emptyClause = clause
                    clauseCnt--
                    canBeSolved = false
                    return false
                }
            } else {
                clauseCnt--
                return false
            }
        }
        return true
    }

    fun copy(): BooleanFormula {
        val newFormula = BooleanFormula()
        newFormula.variables = variables.clone()
        newFormula.clauseCnt = clauseCnt
        newFormula.clauses = clauses.toMutableList()
        newFormula.startClauses = startClauses
        newFormula.unknownVariablesLeft = unknownVariablesLeft
        newFormula.varCnt = varCnt
        return newFormula
    }

    fun setVariable(variable: Int, value: Boolean) {
        if (variables[variable - 1] == null) {
            unknownVariablesLeft--
            variables[variable - 1] = value
            stackOfKnownLiterals.push(variable)
        }
    }

    fun deleteAllUsesOfVariable(variable: Int) {
        val valueOfVariable: Boolean = variables[variable - 1] == true
        clauses = clauses.filter { deleteClauseOrDeleteVariable(variable, valueOfVariable, it) } as MutableList<Clause>
    }

    fun simplify() {
        clauses.filter { !it.hasProposalLiterals }
        clauseCnt = clauses.size
        do {
            while (!stackOfKnownLiterals.empty()) {
                val literal = stackOfKnownLiterals.pop()
                deleteAllUsesOfVariable(literal)
            }
            for (el in clauses) {
                if (el.isLiteral()) {
                    val literal = el.varArray[0]
                    val value = literal > 0
                    setVariable(abs(literal), value)
//                    stackOfKnownLiterals.push(abs(literal))
                }
            }
//            if (unknownVariablesLeft == 0 || isEmpty()) {
//                return
//            }
        } while (!stackOfKnownLiterals.empty())
    }


    private fun isClausePositive(clause: Clause): Boolean {
        if (variables.all { it == null}) return false
        for (el in clause.varArray) {
            if (el > 0 && variables[el - 1] == true) return true
            if (el < 0 && variables[-el - 1] == false) return true
        }
        return false
    }

    fun isAnswerCorrect(): Boolean {
        for (clause in startClauses) {
            if (!isClausePositive(clause)) return false
        }
        return true
    }

}

