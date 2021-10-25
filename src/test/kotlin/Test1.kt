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
        assertEquals(true, solver.formula.isSolved)
        assertEquals(true, solver.formula.canBeSolved)
        assertEquals(true, solver.formula.isAnswerCorrect())
    }

    @Test
    fun `test 2`() {
        val solver = Solver()
        solver.interpretString("p cnf 1 1")
        solver.interpretString("1 0")
        solver.solve()
        assertEquals(true, solver.formula.isSolved)
        assertEquals(true, solver.formula.canBeSolved)
        assertEquals(true, solver.formula.isAnswerCorrect())
    }

    @Test
    fun `test 3`() {
        val solver = Solver()
        solver.interpretString("p cnf 1 1")
        solver.interpretString("1 -1 0")
        solver.solve()
        assertEquals(true, solver.formula.canBeSolved)
        assertEquals(true, solver.formula.isSolved)
        assertEquals(true, solver.formula.isAnswerCorrect())
    }

    @Test
    fun `test 4`() {
        val solver = Solver()
        solver.interpretString("p cnf 2 2")
        solver.interpretString("1 2 0")
        solver.interpretString("-1 -2 0")
        solver.solve()
        assertEquals(true, solver.formula.isSolved)
        assertEquals(true, solver.formula.canBeSolved)
        assertEquals(true, solver.formula.isAnswerCorrect())
    }

    @Test
    fun `test 5`() {
        val solver = Solver()
        solver.interpretString("p cnf 3 3")
        solver.interpretString("1 2 3 0")
        solver.interpretString("-1 -2 0")
        solver.interpretString("-3 0")
        solver.solve()
        assertEquals(true, solver.formula.isSolved)
        assertEquals(true, solver.formula.canBeSolved)
        assertEquals(true, solver.formula.isAnswerCorrect())

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
        assertEquals(true, solver.formula.isSolved)
        assertEquals(true, solver.formula.canBeSolved)
        assertEquals(true, solver.formula.isAnswerCorrect())
    }

    @Test
    fun `test 9`() {
        val solver = Solver()
        solver.interpretString("p cnf 1 2")
        solver.interpretString("1 0")
        solver.interpretString("-1 0")
        solver.solve()
        assertEquals(false, solver.formula.canBeSolved)
    }

    @Test
    fun `test 10`() {
        val solver = Solver()
        solver.interpretString("p cnf 3 6")
        solver.interpretString("1 -2 0")
        solver.interpretString("-1 2 0")
        solver.interpretString("2 -3 0")
        solver.interpretString("3 -2 0")
        solver.interpretString("1 3 0")
        solver.interpretString("-1 -3 0")
        solver.interpretString("1 -3 0")
        solver.solve()
        assertEquals(false, solver.formula.canBeSolved)
    }

    @Test
    fun `test 11`() {
        val solver = Solver()
        solver.interpretString("p cnf 4 4")
        solver.interpretString("1 2 3 0")
        solver.interpretString("-1 -2 0")
        solver.interpretString("3 4 0")
        solver.interpretString("1 -2 0")
        solver.solve()
        assertEquals(true, solver.formula.canBeSolved)
        assertEquals(true, solver.formula.isSolved)
        assertEquals(true, solver.formula.isAnswerCorrect())
    }

    @Test
    fun `test 12`() {
        val solver = Solver()
        solver.interpretString("p cnf 4 1")
        solver.interpretString("1 2 3 4 0")
        solver.solve()
        assertEquals(true, solver.formula.isSolved)
        assertEquals(true, solver.formula.canBeSolved)
        assertEquals(true, solver.formula.isAnswerCorrect())
    }

    @Test
    fun `test 13`() {
        val solver = Solver()
        solver.interpretString("p cnf 4 3")
        solver.interpretString("1 2 0")
        solver.interpretString("2 3 0")
        solver.interpretString("-3 -4 0")
        solver.solve()
        assertEquals(true, solver.formula.isSolved)
        assertEquals(true, solver.formula.canBeSolved)
        assertEquals(true, solver.formula.isAnswerCorrect())
    }

    @Test
    fun `test 14`() {
        val solver = Solver()
        solver.interpretString("p cnf 9 7")
        solver.interpretString("1 6 7 8 0")
        solver.interpretString("2 0")
        solver.interpretString("3 5 0")
        solver.interpretString("4 0")
        solver.interpretString("9 0")
        solver.interpretString("-2 -3 0")
        solver.interpretString("-2 -4 0")
        solver.solve()
        assertEquals(false, solver.formula.canBeSolved)
    }

    @Test
    fun `test 15`() {
        val solver = Solver()
        solver.interpretString("p cnf 4 3")
        solver.interpretString("-1 -2 0")
        solver.interpretString("3 4 0")
        solver.interpretString("-3 -4 0")
        solver.solve()
        assertEquals(true, solver.formula.canBeSolved)

        assertEquals(true, solver.formula.isSolved)
        assertEquals(true, solver.formula.isAnswerCorrect())
    }

    @Test
    fun `test 16`() {
        val solver = Solver()
        solver.interpretString("p cnf 4 2")
        solver.interpretString("1 2 3 4 0")
        solver.interpretString("-1 -2 -3 -4 0")
        solver.solve()
        assertEquals(true, solver.formula.canBeSolved)
        assertEquals(true, solver.formula.isSolved)
        assertEquals(true, solver.formula.isAnswerCorrect())
    }
    @Test
    fun `test 18`() {
        val solver = Solver()
        solver.interpretString("p cnf 2 4")
        solver.interpretString("1 2 0")
        solver.interpretString("1 -2 0")
        solver.interpretString("-1 -2 0")
        solver.interpretString("-1 2 0")
        solver.solve()
        assertEquals(false, solver.formula.canBeSolved)

    }

    @Test
    fun `test 19`() {
        val solver = Solver()
        solver.interpretString("p cnf 2 1")
        solver.interpretString("-1 -2 0")
        solver.solve()
        assertEquals(true, solver.formula.canBeSolved)
        assertEquals(true, solver.formula.isSolved)
        assertEquals(true, solver.formula.isAnswerCorrect())
    }
    @Test
    fun `test 20`() {
        val solver = Solver()
        solver.interpretString("p cnf 4 1")
        solver.interpretString("1 2 3 4 0")
        solver.solve()
        assertEquals(true, solver.formula.canBeSolved)
        assertEquals(true, solver.formula.isSolved)
        assertEquals(true, solver.formula.isAnswerCorrect())
    }
}

