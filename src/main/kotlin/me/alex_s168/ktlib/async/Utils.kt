package me.alex_s168.ktlib.async

import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Returns a new concurrent and mutable collection with the specified elements.
 */
fun <T> concurrentMutableCollectionOf(
    vararg elements: T
): MutableCollection<T> =
    ConcurrentLinkedQueue<T>().also {
        it.addAll(elements)
    }

/**
 * Returns a new concurrent and mutable list with the specified elements.
 */
fun <T> concurrentMutableListOf(
    vararg elements: T
): MutableList<T> =
    CopyOnWriteArrayList<T>().also {
        it.addAll(elements)
    }