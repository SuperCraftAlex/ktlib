package me.alex_s168.ktlib.counter

/**
 * A simple implementation of [Counter] that counts up.
 */
class UpCounter(
    private var count: Int = 0
): Counter<Int> {

    override fun next() {
        count ++
    }

    override fun get(): Int =
        count

}