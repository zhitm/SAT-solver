import java.io.File
import java.io.BufferedReader
import java.lang.Math.abs

class Solver {
    var formula: BooleanFormula = BooleanFormula()
    var needResolution = true
    fun solve() {
//        formula.printState()
        firstIterationOfResolution()
//        formula.printState()
        if (formula.lastLevel.isEmpty()) needResolution = false
        while (!formula.isSolved && formula.canBeSolved) {
            if (formula.unknownVariablesLeft == 0 || formula.isEmpty()) formula.isSolved = true
            if (needResolution)
                iterationOfResolution()
            else bruteForce(formula)
            if (formula.lastLevel.isEmpty()) needResolution = false
        }
        if (!formula.canBeSolved) {
            println("No solution!")
            createGraph(formula.emptyClause)
        } else {
            for (i in formula.variables.indices) {
                if (formula.variables[i] != null)
                    println((i+1).toString() + ": " + formula.variables[i])
                else println("${i+1}: true or false")
            }
            saveSolution()
        }
    }

    private fun firstIterationOfResolution() {
        if (formula.clauseCnt <= 1) return
        for (i in 1 until formula.clauseCnt) {
            for (j in 0 until i) {
                val cl1 = formula.clauses[i]
                val cl2 = formula.clauses[j]
                if (cl1.canBeResolute(cl2))
                    formula.lastLevel.add(cl1.resolute(cl2))
            }
        }
        for (el in formula.lastLevel) {
            if (el.isEmpty()) {
                formula.canBeSolved = false
                formula.emptyClause = el
                return
            }
            if (el.isLiteral()) {
                formula.deleteAllUsesOfVariable(abs(el.varArray[0]), el.varArray[0] > 0, formula.lastLevel)
                formula.deleteAllUsesOfVariable(abs(el.varArray[0]), el.varArray[0] > 0, formula.clauses)
            }
        }
    }

    private fun iterationOfResolution() {
        println("new iteration")
        println(formula.unknownVariablesLeft)
        formula.newLastLevel = mutableListOf<Clause>()
        val llSize = formula.lastLevel.size
        if (llSize >= 2) {
            for (i in 1 until llSize) {
                for (j in 0 until i) {
                    val cl1 = formula.lastLevel[i]
                    val cl2 = formula.lastLevel[j]
                    if (cl1.canBeResolute(cl2))
                        formula.newLastLevel.add(cl1.resolute(cl2))
                }
                for (el in formula.clauses) {
                    val cl1 = formula.lastLevel[i]
                    if (cl1.canBeResolute(el))
                        formula.newLastLevel.add(cl1.resolute(el))
                }
            }
        }
        for (el in formula.clauses) {
            val cl1 = formula.lastLevel[0]
            if (cl1.canBeResolute(el))
                formula.newLastLevel.add(cl1.resolute(el))
        }
        for (el in formula.newLastLevel) {
            if (el.isEmpty()) {
                formula.canBeSolved = false
                return
            }
            if (el.isLiteral()) {
                formula.deleteAllUsesOfVariable(abs(el.varArray[0]), el.varArray[0] > 0, formula.newLastLevel)
                formula.deleteAllUsesOfVariable(abs(el.varArray[0]), el.varArray[0] > 0, formula.lastLevel)
                formula.deleteAllUsesOfVariable(abs(el.varArray[0]), el.varArray[0] > 0, formula.clauses)
            }
        }
        for (el in formula.lastLevel) formula.addClause(el)
        formula.lastLevel = formula.newLastLevel
    }

    private fun bruteForce(formula: BooleanFormula) {
        println("bf started")
        if (formula.isSolved) {
            this.formula = formula
            return
        }
        if (!formula.canBeSolved) return
        for (i in formula.variables.indices) {
            if (formula.variables[i] == null) {
                val formulaWithTrueValue: BooleanFormula = formula.copy()
                val formulaWithFalseValue: BooleanFormula = formula.copy()
                formulaWithTrueValue.setVariable(i+1, true)
                formulaWithFalseValue.setVariable(i+1, false)
                bruteForce(formulaWithTrueValue)
                bruteForce(formulaWithFalseValue)
            }
        }
    }

    fun readFormulaFromFile(path: String) {
        val bufferedReader: BufferedReader = File(path).bufferedReader()
        val inputStrings = bufferedReader.use { it.readLines() }
        for (string in inputStrings) {
            interpretString(string)
        }
//        TODO("обработать ошибки ввода + пользоваться 0 в конце строки")
    }

    private fun createGraph(emptyClause: Clause?) {
//        TODO("")
    }

    private fun saveSolution() {}

    fun getSolution(): Array<Boolean?> {
        return formula.variables
    }

    private fun isClausePositive(clause: Clause): Boolean {
        for (el in clause.varArray) {
            if (el > 0 && formula.variables[el - 1] == true) return true
            if (el < 0 && formula.variables[-el - 1] == false) return true
        }
        return false
    }

    fun isAnswerCorrect(): Boolean {
        for (clause in formula.startClauses) {
            if (!isClausePositive(clause)) return false
        }
        return true
    }

    fun interpretString(string: String) {
        if (string == " ") return
        val inputArray = string.split(" ")
        if (inputArray[0] == "p" && inputArray[1] == "cnf") {
            val varCnt = inputArray[2].toInt()
            formula.setVarsCnt(varCnt)
        } else if (inputArray[0] != "c") {
            val array: MutableList<Int> = inputArray.map { it.toInt() } as MutableList<Int>
            array.remove(0);
            val newClause = Clause(array)
            formula.startClauses.add(newClause.copy())
//            formula.addClause(newClause)
            formula.addClauseFromFile(newClause)
        }
    }
}