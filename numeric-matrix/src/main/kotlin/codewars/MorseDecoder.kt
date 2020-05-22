package codewars

import kotlin.math.min

class MorseDecoder {}

val MORSE_CODE = mapOf(
        "-.-.--" to "!", 
        ".-..-." to "\"", 
        "...-..-" to "$", 
        ".-..." to "&", 
        ".----." to "'", 
        "-.--." to "(", 
        "-.--.-" to ")", 
        ".-.-." to "+", 
        "--..--" to ",", 
        "-....-" to "-", 
        ".-.-.-" to ".", 
        "-..-." to "/", 
        "-----" to "0", 
        ".----" to "1", 
        "..---" to "2", 
        "...--" to "3", 
        "....-" to "4", 
        "....." to "5", 
        "-...." to "6", 
        "--..." to "7", 
        "---.." to "8", 
        "----." to "9", 
        "---..." to ":", 
        "-.-.-." to ";", 
        "-...-" to "=",
        "..--.." to "?", 
        ".--.-." to "@", 
        ".-" to "A", 
        "-..." to "B", 
        "-.-." to "C", 
        "-.." to "D", 
        "." to "E", 
        "..-." to "F", 
        "--." to "G", 
        "...." to "H", 
        ".." to "I", 
        ".---" to "J", 
        "-.-" to "K", 
        ".-.." to "L", 
        "--" to "M", 
        "-." to "N", 
        "---" to "O", 
        ".--." to "P", 
        "--.-" to "Q", 
        ".-." to "R", 
        "..." to "S", 
        "-" to "T", 
        "..-" to "U", 
        "...-" to "V", 
        ".--" to "W", 
        "-..-" to "X", 
        "-.--" to "Y", 
        "--.." to "Z", 
        "..--.-" to "_", 
        "...---..." to "SOS")

private const val CHAR_SEP = " "
private const val WORD_SEP = "   "
private const val DOT = "."
private const val DASH = "-"

fun decodeMorse(code: String) = code.trim().split(WORD_SEP).joinToString(" ")
        { it.split(CHAR_SEP).joinToString("")
        { MORSE_CODE[it] ?: "" } }

fun decodeBits(rawBits: String): String {
    val cleanBits = rawBits.trim('0')
    val shortest = bitRate(cleanBits)
    val beeps= mapOf(bits(shortest) to DOT, bits(shortest * 3) to DASH)
    val (splitToBeeps, splitToLetters, splitToWords) =
            listOf(splitter(shortest), splitter(shortest * 3), splitter(shortest * 7))

    return splitToWords(cleanBits).joinToString(WORD_SEP)
        { splitToLetters(it).joinToString(CHAR_SEP)
        { splitToBeeps(it).joinToString("")
        { beeps[it] ?: DOT } } }
}

private fun bits(len: Int) = "1".repeat(len)

private fun bitRate(bits: String) = "0+|1+".toRegex().findAll(bits).minBy { it.value.length }!!.value.length

// High order function to split by the given length of 0s
private fun splitter(len: Int): (String) -> List<String> = { "0{$len}".toRegex().split(it) }

fun decodeBitsEW(bits: String): String {
    val bis = bits.trim { it == '0' }
    val timeUnit = Regex("0+|1+").findAll(bis).fold(bis.length) { smallest, match -> min(match.value.length, smallest) }
    return bis.replace("111".repeat(timeUnit), "-")
              .replace("000".repeat(timeUnit), " ")
              .replace("1".repeat(timeUnit), ".")
              .replace("0".repeat(timeUnit), "")
}

fun decodeMorseEW(code: String) =
//        code.split(" ").map { if (it == "") " " else MORSE_CODE[it] }.joinToString("")
        code.split(" ").joinToString("") { if (it == "") " " else MORSE_CODE[it] ?: "" }.also { println(code) }

