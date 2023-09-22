package me.alex_s168.ktlib.atomic

/**
 * Every implementation of this interface must be thread-safe!
 */
interface Atomic<T> {

    /**
     * Set the value to [newValue].
     */
    fun set(newValue: T)

    /**
     * Get the value.
     */
    fun get(): T

    /**
     * Get the value and then set it to [newValue].
     */
    fun getAndSet(newValue: T): T

}