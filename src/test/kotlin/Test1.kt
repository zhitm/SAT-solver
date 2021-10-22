import org.junit.Test
import kotlin.test.assertEquals

class HelloJunit5Test {
    @Test
    fun `test 1`() {
        val solver = Solver()
        solver.interpretString("p cnf 3 3")
        solver.interpretString("1 2 0")
        solver.interpretString("-1 3 0")
        solver.interpretString("1 -2 0")
        solver.solve()
        for (el in solver.formula.variables) println(el)
        println()
        assertEquals(true, solver.formula.isSolved)
        assertEquals(true, solver.isAnswerCorrect())
    }
    @Test
    fun `test 2`() {
        val solver = Solver()
        solver.interpretString("p cnf 1 1")
        solver.interpretString("1 0")
        solver.solve()
        for (el in solver.formula.variables) println(el)
        println()
        assertEquals(true, solver.formula.isSolved)
        assertEquals(true, solver.isAnswerCorrect())
    }
    @Test
    fun `test 3`() {
        val solver = Solver()
        solver.interpretString("p cnf 1 1")
        solver.interpretString("1 -1 0")
        solver.solve()
        assertEquals(false, solver.formula.canBeSolved)
    }
    @Test
    fun `test 4`() {
        val solver = Solver()
        solver.interpretString("p cnf 2 2")
        solver.interpretString("1 2 0")
        solver.interpretString("-1 -2 0")
        solver.solve()
        assertEquals(false, solver.formula.canBeSolved)
    }
    @Test
    fun `test 5`() {
        val solver = Solver()
        solver.interpretString("p cnf 3 3")
        solver.interpretString("1 2 3 0")
        solver.interpretString("-1 -2 0")
        solver.interpretString("-3 0")

        solver.solve()
        assertEquals(false, solver.formula.canBeSolved)
    }
    @Test
    fun `test 6`() {
        val solver = Solver()
        solver.interpretString("p cnf 3 4")
        solver.interpretString("1 2 3 0")
        solver.interpretString("2 3 0")
        solver.interpretString("2 -3 0")
        solver.interpretString("-2 0")
        solver.solve()
        assertEquals(false, solver.formula.canBeSolved)
    }
    @Test
    fun `test 7`() {
        val solver = Solver()
        solver.interpretString("p cnf 3 4")
        solver.interpretString("1 2 3 0")
        solver.interpretString("2 -3 0")
        solver.interpretString("2 3 0")
        solver.interpretString("-2 0")
        solver.solve()
        assertEquals(false, solver.formula.canBeSolved)
    }
    @Test
    fun `test 8`() {
        val solver = Solver()
        solver.interpretString("p cnf 3 3")
        solver.interpretString("1 0")
        solver.interpretString("2 0")
        solver.interpretString("3 0")
        solver.solve()
        for (el in solver.formula.variables) println(el)
        println()
        assertEquals(true, solver.formula.isSolved)
        assertEquals(true, solver.isAnswerCorrect())
    }
//    @Test
//    fun `test 238`() {
//        val solver = Solver()
//        solver.interpretString("p cnf 2 1")
//        solver.interpretString("-1 -2 0")
//        solver.solve()
//        assertEquals(true, solver.formula.isSolved)
//    }
//    - тест перебора вариантов, которого нет пока
}

