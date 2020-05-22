package codewars

//fun persistence(num: Int): Int {
//    fun helper(n: Int, count: Int): Int = if (n < 10) count else helper(productOfDigits(n), count + 1)
//    return helper(num, 0)
//}
//

import kotlin.math.max

/**
 * Calculates the product of all digits in given number
 */
fun productOfDigits(num: Int) = num.toString().map { it - '0' }.reduce(Int::times)

/**
 * Calculates the multiplicative persistence of the given number. This is, the number
 * of times you have to multiply the digits of the number to get a single digit.
 */
fun persistence(num: Int) = generateSequence(num) { productOfDigits(it) }.takeWhile { it > 9 }.count()

fun maxSequence(nums: IntArray): Int {
    var best = 0
    return nums.fold(0) { sum, n -> max(0, sum + n).also { best = max(it, best) } }.let { best }
}
