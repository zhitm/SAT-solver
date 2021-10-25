import java.io.File
import java.io.BufferedReader

class Solver {
    var formula: BooleanFormula = BooleanFormula()
    var needResolution = true

    fun solve() {
        while (formula.canBeSolved && needResolution) {
            if (needResolution)
                resolute()
            if (formula.lastLevel.isEmpty()) needResolution = false
        }
        if (!formula.canBeSolved) {
            println("No solution!")
            createGraph()
        } else {
            formula.simplify()
            if (!formula.isSolved)
                bruteForce(formula)
            for (i in formula.variables.indices) {
                if (formula.variables[i] != null)
                    println((i + 1).toString() + ": " + formula.variables[i])
                else println("${i + 1}: true or false")
            }
        }
    }

    private fun resolute() {
        if (formula.lastLevel.isEmpty()) firstIterationOfResolution()
        else iterationOfResolution()
    }


    private fun firstIterationOfResolution() {
        if (formula.clauseCnt <= 1) return
        for (i in 1 until formula.clauseCnt) {
            for (j in 0 until i) {
                val cl1 = formula.clauses[i]
                val cl2 = formula.clauses[j]
                for (variable in cl1.varArray) {
                    if (cl1.canBeResolute(cl2, variable)) {
                        val newClause = cl1.resolute(cl2, variable)
                        if (newClause.isEmpty()) formula.emptyClause = newClause
                        if (!formula.hasClause(newClause) && !newClause.hasProposalLiterals)
                            formula.lastLevel.add(newClause)
                    }
                }
            }
        }
        for (el in formula.lastLevel) {
            if (el.isEmpty()) {
                formula.canBeSolved = false
                formula.emptyClause = el
                return
            }
        }
    }

    private fun iterationOfResolution() {
        formula.newLastLevel = mutableListOf<Clause>()
        val llSize = formula.lastLevel.size
        if (llSize >= 2) {
            for (i in 1 until llSize) {
                for (j in 0 until i) {
                    val cl1 = formula.lastLevel[i]
                    val cl2 = formula.lastLevel[j]
                    for (variable in cl1.varArray)
                        if (cl1.canBeResolute(cl2, variable)) {
                            val newClause = cl1.resolute(cl2, variable)
                            if (newClause.isEmpty()) formula.emptyClause = newClause

                            if (!newClause.hasProposalLiterals && !formula.hasClauseAtNewLevels(newClause) && !formula.hasClause(
                                    newClause
                                )
                            )
                                formula.newLastLevel.add(newClause)
                        }
                }
                for (el in formula.clauses) {
                    val cl1 = formula.lastLevel[i]
                    for (variable in cl1.varArray)
                        if (cl1.canBeResolute(el, variable)) {
                            val newClause = cl1.resolute(el, variable)
                            if (newClause.isEmpty()) formula.emptyClause = newClause

                            if (!newClause.hasProposalLiterals && !formula.hasClauseAtNewLevels(newClause) && !formula.hasClause(
                                    newClause
                                )
                            )
                                formula.newLastLevel.add(newClause)
                        }
                }
            }
        }
        for (el in formula.clauses) {
            val cl1 = formula.lastLevel[0]
            for (variable in cl1.varArray)
                if (cl1.canBeResolute(el, variable)) {
                    val newClause = cl1.resolute(el, variable)
                    if (newClause.isEmpty()) formula.emptyClause = newClause

                    if (!newClause.hasProposalLiterals && !formula.hasClauseAtNewLevels(newClause) && !formula.hasClause(newClause))
                        formula.newLastLevel.add(newClause)
                }
        }
        for (el in formula.newLastLevel) {
            if (el.isEmpty()) {
                formula.canBeSolved = false
                return
            }
        }
        for (el in formula.lastLevel) {
            if (!el.hasProposalLiterals && !formula.hasClause(el))
                formula.addClause(el)
        }
        formula.lastLevel = formula.newLastLevel
    }

    private fun bruteForce(formula: BooleanFormula) {
        if (formula.unknownVariablesLeft == 0 && formula.isAnswerCorrect()) {
            formula.isSolved = true
        }

        if (formula.isSolved) {
            this.formula = formula
            return
        }
        for (i in formula.variables.indices) {
            if (formula.variables[i] == null) {
                val formulaWithTrueValue: BooleanFormula = formula.copy()
                val formulaWithFalseValue: BooleanFormula = formula.copy()
                formulaWithTrueValue.setVariable(i + 1, true)
                formulaWithFalseValue.setVariable(i + 1, false)
                formulaWithTrueValue.simplify()
                formulaWithFalseValue.simplify()
//                лаааааааааажааааааа
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

    private fun createGraph() {
        val graph = Graph()
        fillGraph(graph, formula.emptyClause!!)
        graph.createGraphvizFile()
    }

    private fun fillGraph(graph: Graph, clause: Clause) {
        val current = graph.getNode(clause)
        if (clause.parent1 != null && clause.parent2 != null) {
            current.addChild(Node(clause.parent1))
            current.addChild(Node(clause.parent2))
            graph.addEdge(current, current.child1!!)
            graph.addEdge(current, current.child2!!)
            fillGraph(graph, clause.parent1)
            fillGraph(graph, clause.parent2)
        }

    }

    private fun saveSolution() {}

    fun getSolution(): Array<Boolean?> {
        return formula.variables
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
            formula.addClause(newClause)
//            formula.addClauseFromFile(newClause)
        }
    }
}