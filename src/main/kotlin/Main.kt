fun main(args: Array<String>) {
    val solver: Solver = Solver()
    solver.readFormulaFromFile("/home/maria/IdeaProjects/SAT-solver/src/main/kotlin/test.txt")
    solver.solve()

}