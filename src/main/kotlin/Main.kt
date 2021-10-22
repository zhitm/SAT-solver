fun main(args: Array<String>) {
    val solver: Solver = Solver()
    solver.readFormulaFromFile("src/main/kotlin/test.txt")
    solver.solve()

}