package codewars

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

internal class MorseDecoderTest {

    @Test
    fun exampleTestCases() {
        assertEquals("HEY JUDE", decodeMorse(decodeBits("0001100110011001100000011000000111111001100111111001111110000000000000011001111110011111100111111000000110011001111110000001111110011001100000011000000000")))
    }

    @ParameterizedTest
    @ValueSource(strings = ["01110", "0000000111100000", "1", "111", "1111111"])
    fun `should produce E`(rawBits: String) {
        assertEquals("E", decodeMorse(decodeBits(rawBits)))
    }

    @ParameterizedTest
    @ValueSource(strings = ["111000000000111", "10001"])
    fun `should produce EE`(rawBits: String) {
        assertEquals("EE", decodeMorse(decodeBits(rawBits)))
    }

    @ParameterizedTest
    @ValueSource(strings = ["0011100011100", "101", "110011", "111000111", "111110000011111"])
    fun `should produce I`(rawBits: String) {
        assertEquals("I", decodeMorse(decodeBits(rawBits)))
    }

    @ParameterizedTest
    @ValueSource(strings = ["10111"])
    fun `should produce A`(rawBits: String) {
        assertEquals("A", decodeMorse(decodeBits(rawBits)))
    }

    @ParameterizedTest
    @ValueSource(strings = ["11111100111111", "1110111"])
    fun `should produce M`(rawBits: String) {
        assertEquals("M", decodeMorse(decodeBits(rawBits)))
    }

    @ParameterizedTest
    @ValueSource(strings = ["111000111000111"])
    fun `should produce S`(rawBits: String) {
        assertEquals("S", decodeMorse(decodeBits(rawBits)))
    }

    @ParameterizedTest
    @CsvSource(
        "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG., 00011100010101010001000000011101110101110001010111000101000111010111010001110101110000000111010101000101110100011101110111000101110111000111010000000101011101000111011101110001110101011100000001011101110111000101011100011101110001011101110100010101000000011101110111000101010111000100010111010000000111000101010100010000000101110101000101110001110111010100011101011101110000000111010100011101110111000111011101000101110101110101110",
        "HEY JUDE, 11001100110011000000110000001111110011001111110011111100000000000000110011111100111111001111110000001100110011111100000011111100110011000000110000000"
    )
    fun `should handle longer sequences`(phrase: String, longRawBits: String) {
        assertEquals(phrase, decodeMorse(decodeBits(longRawBits)))
    }
}
