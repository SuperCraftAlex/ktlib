package me.alex_s168.ktlib.async

import me.alex_s168.ktlib.atomic.inc
import me.alex_s168.ktlib.atomic.num.AtomicInt
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Executes the given function for each element of this collection asynchronously.
 * @param process the function to execute.
 */
fun <T> Iterable<T>.forEachAsync(
    process: (T) -> Unit
) {
    val tasks = mutableListOf<AsyncTask>()
    forEach {
        tasks += async {
            process(it)
        }
    }
    tasks.await()
}

/**
 * Maps the elements of this collection asynchronously.
 * (Useful for when you don't care about the order of the elements.)
 * @param process the function to execute.
 */
fun <T, E> Iterable<T>.mapAsync(
    process: (T) -> E
): MutableCollection<E> {
    val tasks = mutableListOf<AsyncTask>()
    val result = concurrentMutableCollectionOf<E>()
    forEach {
        tasks += async {
            result += process(it)
        }
    }
    tasks.await()
    return result
}

/**
 * Returns the number of elements in this collection.
 */
fun <T> Iterable<T>.count(): Int {
    if (this is Collection) return size
    val count = AtomicInt(0)
    forEachAsync {
        count.inc()
    }
    return count.get()
}

/**
 * Returns a thread safe mutable collection containing all elements of this collection.
 */
fun <T> Iterable<T>.toMutableConcurrentCollection(): MutableCollection<T> {
    val set = ConcurrentLinkedQueue<T>()
    forEachAsync {
        set += it
    }
    return set
}

/**
 * Awaits all tasks in the iterable.
 */
fun Iterable<AsyncTask>.await() =
    forEach {
        if (it.isAlive())
            it.await()
    }

/**
 * Cancels all tasks in the iterable.
 */
fun Iterable<AsyncTask>.cancel() =
    forEach {
        if (it.isAlive())
            it.stop()
    }