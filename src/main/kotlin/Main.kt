import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val time = measureTimeMillis {
        val solver: Solver = Solver()
        solver.readFormulaFromFile("src/main/cnf_examples/test7.txt")
        solver.solve()
    }
    println("Time to solve in seconds: ${time/1000}")
}