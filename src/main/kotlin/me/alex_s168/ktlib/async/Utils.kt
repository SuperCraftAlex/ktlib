package me.alex_s168.ktlib.async

import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Returns a new concurrent and mutable collection with the specified elements.
 */
fun <T> concurrentMutableCollectionOf(
    vararg elements: T
): MutableCollection<T> =
    ConcurrentLinkedQueue<T>().also {
        it.addAll(elements)
    }