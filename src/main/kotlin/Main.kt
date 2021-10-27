import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val time = measureTimeMillis {
        val solver = Solver()
        val path = args[0]
        solver.readFormulaFromFile(path)
        solver.solve(  )
        solver.printResult()
    }
    println("Time to solve in seconds: ${time / 1000}")
}
