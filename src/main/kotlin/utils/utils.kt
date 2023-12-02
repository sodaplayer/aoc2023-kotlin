package utils

import java.io.InputStream

fun loadInput(path: String): InputStream {
    return checkNotNull({}::class.java.getResourceAsStream(path))
}

fun loadLines(path: String): List<String> {
    return checkNotNull({}::class.java.getResourceAsStream(path))
        .bufferedReader()
        .readLines()
}

fun <T> Iterable<T>.head(): T = this.first()
fun <T> Iterable<T>.tail(): Iterable<T> = this.drop(1)

fun <T> Iterable<T>.car(): T = this.first()
fun <T> Iterable<T>.cdr(): Iterable<T> = this.drop(1)

fun <T> T.alsoPrintln(prefix: String = ""): T {
    println("$prefix $this")
    return this
}

fun <T> Iterable<T>.prettyPrint(prefix: String = ""): Iterable<T> {
    println(prefix)
    this.forEach { println(it) }

    return this
}

/* Based on JetBrain's Sequence library which is licensed by the Apache 2.0 license.
 * https://github.com/JetBrains/kotlin/blob/a829a6743d03509659a88bc49b25ae0abe230343/libraries/stdlib/src/kotlin/collections/Sequences.kt
 */
fun <T> Sequence<T>.partitionWhen(predicate: (T, T) -> Boolean): Sequence<List<T>>
        = PartitionWhenSequence(this, predicate)

fun <T> Iterable<T>.partitionWhen(predicate: (T, T) -> Boolean): List<List<T>>
        = PartitionWhenSequence(this.asSequence(), predicate).toList()


private class PartitionWhenSequence<T>
    constructor(private val source: Sequence<T>, private val predicate: (T, T) -> Boolean) : Sequence<List<T>> {
    override fun iterator(): Iterator<List<T>> = PartitionWhenIterator(source.iterator(), predicate)
}

private class PartitionWhenIterator<T>(
    private val source: Iterator<T>,
    private val predicate: (T, T) -> Boolean
) : AbstractIterator<List<T>>() {
    private val buffer: ArrayList<T> = arrayListOf()

    override fun computeNext() {
        while (source.hasNext()) {
            val next = source.next()

            if (buffer.isEmpty()) {
                buffer.add(next)
            } else {
                if (predicate(buffer.last(), next)) {
                    setNext(buffer.toList())
                    buffer.clear()

                    return
                } else {
                    buffer.add(next)
                }
            }
        }

        if (buffer.isNotEmpty()) {
            setNext(buffer.toList())
            buffer.clear()
        } else {
            done()
        }
    }
}
