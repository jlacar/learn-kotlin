package numeric.matrix

import numeric.matrix.MatrixTransposeType.*

class DoubleMatrix(val rows: Int, val cols: Int) {
    val size = Pair(rows, cols)

    private val values = Array(rows) { DoubleArray(cols) { 0.0 } }

    override fun toString(): String = values.joinToString("\n") { it.joinToString(" ") }

    fun isSquare() = rows == cols
    fun isNotSquare() = !isSquare()

    fun transpose(strategy: MatrixTransposeType = MAIN_DIAGONAL): DoubleMatrix {
        val (cols, rows) = this.size
        val transposed = DoubleMatrix(rows, cols)
        val transposeFn = strategy.mapper()
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                transposed[row][col] = transposeFn(this, row, col)
            }
        }
        return transposed
    }

    fun determinant(): Double? {
        if (isNotSquare()) return null
        return 0.0
    }

    operator fun set(row: Int, values: DoubleArray) {
        this.values[row] = values
    }

    operator fun set(row: Int, col: Int, value: Double) {
        this.values[row][col] = value
    }

    operator fun get(row: Int): DoubleArray = values[row]

    operator fun get(row: Int, col: Int): Double = values[row][col]

    operator fun plus(other: DoubleMatrix): DoubleMatrix? {
        if (this.size != other.size) return null
        val matrixSum = DoubleMatrix(rows, cols)
        values.mapIndexed { i, row -> matrixSum[i] = sum(row, other[i]) }
        return matrixSum
    }

    private fun sum(a: DoubleArray, b: DoubleArray): DoubleArray =
            a.mapIndexed { i, n -> n + b[i] }.toDoubleArray()

    operator fun times(scalar: Double): DoubleMatrix {
        val scalarProduct = DoubleMatrix(rows, cols)
        values.mapIndexed { i, row -> scalarProduct[i] = product(scalar, row) }
        return scalarProduct
    }

    private fun product(scalar: Double, a: DoubleArray): DoubleArray =
            a.map { it * scalar }.toDoubleArray()

    operator fun times(other: DoubleMatrix): DoubleMatrix? {
        if (this.cols != other.rows) return null
        val product = DoubleMatrix(this.rows, other.cols)
        val otherColumns = other.transpose()
        for (row in 0 until this.rows) {
            for (col in 0 until other.cols) {
                product[row][col] = product(values[row], otherColumns[col])
            }
        }
        return product
    }

    private fun product(a: DoubleArray, b: DoubleArray): Double =
            a.foldIndexed(0.0) { i, sum, value -> sum + value * b[i] }
}

// Make scalar multiplication commutative: scalar * Matrix == Matrix * scalar
operator fun Double.times(matrix: DoubleMatrix) = matrix.times(this)

/**
 * A function that calculates the value in the given Matrix that
 * maps to the given row and column of the transposed Matrix.
 * That is, given matrix a and the transpose function fn(),
 * then fn(a, row, col) ==> a.transposed(row, col)
 */
private typealias TransposeFunction = (DoubleMatrix, Int, Int) -> Double

enum class MatrixTransposeType {
    MAIN_DIAGONAL {
        override fun mapper(): TransposeFunction = { a, row, col -> a[col][row] }
    },
    SIDE_DIAGONAL {
        override fun mapper(): TransposeFunction = { a, row, col -> a[a.cols - col - 1][a.rows - row - 1] }
    },
    VERTICAL_LINE {
        override fun mapper(): TransposeFunction = { a, row, col -> a[row][a.cols - col - 1] }
    },
    HORIZONTAL_LINE {
        override fun mapper(): TransposeFunction = { a, row, col -> a[a.rows - row - 1][col] }
    };

    abstract fun mapper(): TransposeFunction
}
