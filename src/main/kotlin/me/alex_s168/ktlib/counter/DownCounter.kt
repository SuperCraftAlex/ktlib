package me.alex_s168.ktlib.counter

/**
 * A simple implementation of [Counter] that counts down.
 */
class DownCounter(
    private var count: Int = 0
): Counter<Int> {

    override fun next() {
        count --
    }

    override fun get(): Int =
        count

}