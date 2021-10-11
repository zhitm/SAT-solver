import com.sun.org.apache.xpath.internal.operations.Variable
import kotlin.math.abs
import kotlin.math.sign

class BooleanFormula(var varCnt: Int, var clauseCnt: Int) {
    private val variables = arrayOfNulls<Boolean>(varCnt)
    var canBeSolved = true
    var solved = false
    var unknownVariablesLeft = varCnt;
    val clauses: MutableList<Clause> = mutableListOf()
    var emptyClause: Clause? = null

    fun addClause(clause: Clause) {
        clause.simplify()
        if (clause.length == 0) {
            canBeSolved = false
            emptyClause = clause
        } else if (clause.length == 1) {
            val literal: Int = clause.varArray[0]
            deleteVariable(abs(literal), literal > 0)
        } else {
            if (!hasClause(clause)) {
                clauses.add(clause)
                clauseCnt++
            }
        }
    }

    private fun hasClause(clause: Clause): Boolean {
        for (cl in clauses) {
            if (cl.varArray == clause.varArray) return true
        }
        return false
    }

    private fun deleteVariable(variable: Int, value: Boolean) {
        variables[variable] = value
        unknownVariablesLeft--
        if (unknownVariablesLeft == 0) solved = true
        for (clause in clauses) {
            if (variable in clause.varArray) {
                if (value) {
                    clauses.remove(clause)
                    clauseCnt--
                } else {
                    clause.varArray.remove(variable)
                    clause.length--
                }
            } else if (-variable in clause.varArray) {
                if (value) {
                    clause.varArray.remove(variable)
                    clause.length--
                } else {
                    clauses.remove(clause)
                    clauseCnt--
                }
            }
            if (unknownVariablesLeft == 0 || clauseCnt == 0) {
                solved = true
                return
            }
            if (clause.length == 1) deleteVariable(abs(clause.varArray[0]), clause.varArray[0] > 0)
        }
    }
}
