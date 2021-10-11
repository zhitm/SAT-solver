import java.io.File
import java.io.BufferedReader

class Solver {
    private var formula: BooleanFormula = BooleanFormula(0,0)
    fun solve() {
        while (!formula.solved || formula.canBeSolved) {
            TODO("выбор пар для резолюции+перебор, когда нечего резолировать")
        }
        if (!formula.canBeSolved) createGraph(formula.emptyClause)
    }

    fun readFormulaFromFile(path: String) {
        val bufferedReader: BufferedReader = File(path).bufferedReader()
        val inputStrings = bufferedReader.use { it.readLines() }
        for (string in inputStrings) {
            if (string == " ") continue
            val inputArray = string.split(" ")
            if (inputArray[0] == "p" && inputArray[1] == "cnf") {
                val varCnt = inputArray[2].toInt()
                val clauseCnt = inputArray[3].toInt()
                formula.varCnt = varCnt
                formula.clauseCnt = clauseCnt
            } else if (inputArray[0] != "c") {
                val array: MutableList<Int> = inputArray.map { it.toInt() } as MutableList<Int>
                array.remove(0);
                val newClause = Clause(array)
                formula.addClause(newClause)
            }
        }
        TODO("обработать ошибки ввода + пользоваться 0 в конце строки")
    }

    private fun createGraph(emptyClause: Clause?){
        TODO("научиться сохранять историю клоза. видимо, придется заменять клозы вместо того, чтобы удалять из них элементы" +
                "создать граф")
    }
}