package codewars

import java.math.BigInteger
import java.util.regex.Pattern

/**
 * Counts how many letters (non-case-sensitive) occur
 * more than once in the given string.
 */
fun duplicateCount(text: String) =
    text.groupBy(Char::toLowerCase).values.count { it.size > 1 }
//  text.toLowerCase().groupingBy { it }.eachCount().count { it.value > 1 }
//  text.groupingBy(Char::toLowerCase).eachCount().count { it.value > 1 }
//  text.groupingBy(Char::toLowerCase).eachCount().values.count { it > 1 }
//  text.groupingBy { it.toLowerCase() }.eachCount().count { it.value > 1 }

fun inArray(array1: Array<String>, array2: Array<String>): Array<String> {
    return array1.filter { s -> array2.find { it.contains(s) } != null }.sorted().toTypedArray()
}

fun backwardsPrime(start:Long, end:Long):String {
    fun isPrime(n: Long) = BigInteger.valueOf(n).isProbablePrime(100)
    fun isPrimeBackwards(n: Long): Boolean {
        val backwards = n.toString().reversed().toLong()
        return n != backwards && isPrime(backwards)
    }
    return (start..end).filter { isPrime(it) && isPrimeBackwards(it) }.joinToString(" ")
}

fun primes(first: Long, last: Long): Sequence<Long> {
    var i = first
    return (sequence { generateSequence { i++ }
        .filter { n -> n > 1 && ((2 until n).none { n % it == 0L }) }
        .forEach { yield(it) }
    }).takeWhile { it <= last }
}

fun isPrime(n: Long) = n in primes(n, n)

// Generalize to handle any chunk size
fun chunkPattern(size: Int): Pattern = "(?<=\\G.{$size})".toPattern()
fun chunkRegex(size: Int): Regex = "(?<=\\G.{$size})".toRegex()

// fun CharSequence.split(pattern: Pattern, limit: Int = 0): Array<String> = pattern.split(this, limit)