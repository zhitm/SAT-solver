fun main(args: Array<String>) {
    val solver: Solver = Solver()
    solver.readFormulaFromFile("src/main/kotlin/test2.txt")
    solver.solve()
}