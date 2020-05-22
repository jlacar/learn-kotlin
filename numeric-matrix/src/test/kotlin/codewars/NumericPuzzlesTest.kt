package codewars

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class NumericPuzzlesTest {
    @ParameterizedTest
    @CsvSource(
        "21, 2",
        "55, 25",
        "811, 8"
    )
    fun `Product of digits`(n: Int, product: Int) {
        assertEquals(product, productOfDigits(n))
    }

    @Test
    fun `Basic Tests`() {
        assertEquals(3, persistence(39))
        assertEquals(0, persistence(4))
        assertEquals(2, persistence(25))
        assertEquals(4, persistence(999))
    }

    @Test
    fun `maxSequence empty array should be 0`() {
        assertEquals( 0, maxSequence(IntArray(0)))
    }

    @Test
    fun `maxSequence non-empty array`() {
        assertEquals(6, maxSequence(intArrayOf(-2, 1, -3, 4, -1, 2, 1, -5, 4)))
    }
}