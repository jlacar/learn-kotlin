package codewars

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.regex.Pattern

internal class RegexPatternTest {

    private val regexString = "(?<=\\G.{2})"
    private val byRegex: Regex = regexString.toRegex()
    private val byPattern: Pattern = Pattern.compile(regexString)

    private companion object {
        @JvmStatic
        fun allStrings() = arrayOf("a", "ab", "abc", "abcd", "abcde", "abcdef")

        @JvmStatic
        fun oddLengthStrings() = arrayOf("a", "abc", "abcde")

        @JvmStatic
        fun evenLengthStrings() = arrayOf("ab", "abcd", "abcdef")
    }

    @ParameterizedTest
    @MethodSource("allStrings")
    fun `split should be symmetric for Regex and String`(s: String) {
        assertEquals(byRegex.split(s).size, s.split(byRegex).size, "byRegex.split(s).size != s.split(byRegex).size")
    }

    @ParameterizedTest
    @MethodSource("evenLengthStrings")
    fun `Even-length strings - split should symmetric for Pattern and String`(s: String) {
        assertEquals(byPattern.split(s).size, s.split(byPattern).size, "byPattern.split(s).size != s.split(byPattern).size")
    }

    @ParameterizedTest(name = """Pattern.split("{0}").size should be {1}""")
    @MethodSource("oddLengthStrings")
    fun `Odd-length strings - split should symmetric for Pattern and String`(s: String) {
        assertEquals(byPattern.split(s).size, s.split(byPattern).size, "byPattern.split(s).size != s.split(byPattern).size")
    }

    @ParameterizedTest(name = """Pattern.split("{0}").size should be {1}""")
    @CsvSource(
            "a, 1",
            "ab, 1",
            "abc, 2",
            "abcd, 2",
            "abcde, 3",
            "abcdef, 3"
    )
    fun `using Pattern`(s: String, expectedPairs: Int) {
        assertEquals(expectedPairs, byPattern.split(s).size, """byPattern.split("$s").size""")
    }

    @ParameterizedTest(name = """Regex.split("{0}").size should be {1}""")
    @CsvSource(
            "a, 1",
            "ab, 1",
            "abc, 2",
            "abcd, 2",
            "abcde, 3",
            "abcdef, 3"
    )
    fun `using Regex`(s: String, expectedPairs: Int) {
        assertEquals(expectedPairs, byRegex.split(s).size, """byRegex.split("$s").size""")
    }

}