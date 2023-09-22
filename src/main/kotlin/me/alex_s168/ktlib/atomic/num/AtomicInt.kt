package me.alex_s168.ktlib.atomic.num

import java.util.concurrent.atomic.AtomicInteger

class AtomicInt(
    initialValue: Int = 0
): AtomicNum<Int>() {

    private val atomic: AtomicInteger =
        AtomicInteger(initialValue)

    override fun set(newValue: Int) {
        atomic.set(newValue)
    }

    override fun addNew(value: Int): Int =
        get() + value

    override fun subNew(value: Int): Int =
        get() - value

    override fun mulNew(value: Int): Int =
        get() * value

    override fun divNew(value: Int): Int =
        get() / value

    override fun remNew(value: Int): Int =
        get() % value

    override fun one(): Int =
        1

    override fun get(): Int
        = atomic.get()

    override fun getAndSet(newValue: Int): Int {
        return atomic.getAndSet(newValue)
    }

    override fun toString(): String =
        get().toString()

    override fun toByte(): Byte {
        return get().toByte()
    }

    override fun toDouble(): Double {
        return get().toDouble()
    }

    override fun toFloat(): Float {
        return get().toFloat()
    }

    override fun toInt(): Int {
        return get()
    }

    override fun toLong(): Long {
        return get().toLong()
    }

    override fun toShort(): Short {
        return get().toShort()
    }

}