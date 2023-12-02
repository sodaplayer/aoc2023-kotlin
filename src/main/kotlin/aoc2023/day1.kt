package aoc2023

import utils.loadInput

fun main() {
    val lines = loadInput("/2023/day1")
        .bufferedReader()
        .readLines()

    // Part 1
    val numerals = lines.map { line -> line.filter { it.isDigit() } }
    val pairs = numerals
        .map { "${it.first()}${it.last()}" }
        .map(String::toInt)

    val sum = pairs.sum()
    println(sum)

    lines.sumOf { line ->
        val singles = line.windowed(1).mapIndexed { i, s ->
            i to if (s[0].isDigit()) s.toInt() else null
        }.filter { it.second != null }

        val threes = line.windowed(3).mapIndexed { i, s ->
            i to when (s) {
                "one" -> 1
                "two" -> 2
                "six" -> 6
                else -> null
            }
        }.filter { it.second != null }

        val fours = line.windowed(4).mapIndexed { i, s ->
            i to when (s) {
                "four" -> 4
                "five" -> 5
                "nine" -> 9
                else -> null
            }
        }.filter { it.second != null }

        val fives = line.windowed(5).mapIndexed { i, s ->
            i to when (s) {
                "three" -> 3
                "seven" -> 7
                "eight" -> 8
                else -> null
            }
        }.filter { it.second != null }


        (singles + threes + fours + fives)
            .sortedBy { it.first }
            .let { (it.first().second!! * 10) + it.last().second!! }

    }.also { println(it) }
}
