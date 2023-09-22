package me.alex_s168.ktlib.counter

/**
 * A counter is a class that can be used to count in any direction.
 */
interface Counter<E> {

    /**
     * Advances the counter by one.
     */
    fun next()

    /**
     * Returns the current value of the counter.
     */
    fun get(): E

}