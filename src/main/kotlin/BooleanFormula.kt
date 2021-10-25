import java.util.* // ktlint-disable no-wildcard-imports
import kotlin.math.abs

class BooleanFormula {
    var startClauses: MutableList<Clause> = mutableListOf()
    private var varCnt: Int = 0
    var clauseCnt: Int = 0
    var variables = arrayOfNulls<Boolean>(varCnt)
    var canBeSolved = true
    var isSolved = false
    var unknownVariablesLeft = varCnt
    var clauses: MutableList<Clause> = mutableListOf()
    var emptyClause: Clause? = null
    var lastLevel: MutableList<Clause> = mutableListOf()
    var newLastLevel: MutableList<Clause> = mutableListOf()
    private val stackOfKnownLiterals: Stack<Int> = Stack()

    fun setVarsCnt(cnt: Int) {
        varCnt = cnt
        variables = arrayOfNulls(varCnt)
        unknownVariablesLeft = varCnt
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

    private fun deleteClauseOrDeleteVariable(variable: Int, value: Boolean, clause: Clause): Boolean {
        if (variable in clause.varArray) {
            if (value) {
                clauseCnt--
                return false
            } else {
                clause.varArray.remove(variable)
                clause.length--
                if (clause.isEmpty()) {
                    markFormulaAsSolved(clause)
                    return false
                }
            }
        } else if (-variable in clause.varArray) {
            if (value) {
                clause.varArray.remove(-variable)
                clause.length--
                if (clause.isEmpty()) {
                    markFormulaAsSolved(clause)
                    return false
                }
            } else {
                clauseCnt--
                return false
            }
        }
        return true
    }

    private fun markFormulaAsSolved(emptyClause: Clause) {
        this.emptyClause = emptyClause
        clauseCnt--
        canBeSolved = false
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

    private fun deleteAllUsesOfVariable(variable: Int) {
        val valueOfVariable: Boolean = variables[variable - 1] == true
        clauses = clauses.filter { deleteClauseOrDeleteVariable(variable, valueOfVariable, it) } as MutableList<Clause>
    }

    fun simplifyAndFindSomeVariables() {
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
                }
            }
        } while (!stackOfKnownLiterals.empty())
    }

    private fun isClausePositive(clause: Clause): Boolean {
        if (variables.all { it == null }) return false
        for (literal in clause.varArray) {
            if (literal > 0 && variables[literal - 1] == true) return true
            if (literal < 0 && variables[-literal - 1] == false) return true
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
