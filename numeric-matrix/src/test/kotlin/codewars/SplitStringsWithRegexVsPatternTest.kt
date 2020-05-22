package codewars

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SplitStringsWithRegexVsPatternTest {

    private val expr = "(?<=\\G.{2})"
    private val regex = expr.toRegex()
    private val pattern = expr.toPattern()

    private fun withTrailingEmpties() = arrayOf("ab", "abcd", "abcdef")
    private fun withoutTrailingEmpties() = arrayOf("", "a", "abc", "abcde")

    private fun keepsTrailingEmpties(results: List<String>) = results.last() == ""
    private fun discardsTrailingEmpties(results: List<String>) = !keepsTrailingEmpties(results)

    @ParameterizedTest(name = "splitting \"{0}\"")
    @MethodSource("withTrailingEmpties")
    fun `Kotlin keeps trailing empty strings by default`(s: String) {
        assertAll(
                { assertTrue(keepsTrailingEmpties(s.split(pattern)), "s.split(pattern)") },
                { assertTrue(keepsTrailingEmpties(s.split(regex)), "s.split(regex)") },
                { assertTrue(keepsTrailingEmpties(regex.split(s)), "regex.split(s)") }
        )
    }

    @ParameterizedTest(name = "splitting \"{0}\"")
    @MethodSource("withTrailingEmpties")
    fun `Pattern keeps trailing empty strings with negative limit`(s: String) {
        assertTrue(keepsTrailingEmpties(pattern.split(s, -1).toList()))
    }

    @ParameterizedTest(name = "splitting \"{0}\"")
    @MethodSource("withTrailingEmpties")
    fun `Pattern discards trailing empty strings by default`(s: String) {
        assertTrue(discardsTrailingEmpties(pattern.split(s).toList()))
    }

    @ParameterizedTest(name = "splitting \"{0}\"")
    @MethodSource("withoutTrailingEmpties")
    fun `Pattern and Kotlin give the same results when no trailing empty strings`(s: String) {
        assertAll(
                { assertTrue(pattern.split(s).toList() == s.split(pattern)) },
                { assertTrue(pattern.split(s).toList() == s.split(regex)) },
                { assertTrue(pattern.split(s).toList() == regex.split(s)) }
        )
    }

    @ParameterizedTest(name = "splitting \"{0}\"")
    @MethodSource("withTrailingEmpties")
    fun `Pattern and Kotlin give different results when there are trailing empty strings`(s: String) {
        assertAll(
                { assertTrue(pattern.split(s).toList() != s.split(pattern)) },
                { assertTrue(pattern.split(s).toList() != s.split(regex)) },
                { assertTrue(pattern.split(s).toList() != regex.split(s)) }
        )
    }
}