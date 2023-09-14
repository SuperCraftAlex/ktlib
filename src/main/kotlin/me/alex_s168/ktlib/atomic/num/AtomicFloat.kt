package me.alex_s168.ktlib.atomic.num

import java.lang.Float.floatToIntBits
import java.lang.Float.intBitsToFloat

import java.util.concurrent.atomic.AtomicInteger

class AtomicFloat(
    initialValue: Float = 0f
): AtomicNum<Float>() {
    private val bits: AtomicInteger

    init {
        bits = AtomicInteger(floatToIntBits(initialValue))
    }

    override fun set(newValue: Float) {
        bits.set(floatToIntBits(newValue))
    }

    override fun addNew(value: Float): Float =
        get() + value

    override fun subNew(value: Float): Float =
        get() - value

    override fun mulNew(value: Float): Float =
        get() * value

    override fun divNew(value: Float): Float =
        get() / value

    override fun remNew(value: Float): Float =
        get() % value

    override fun one(): Float =
        1f

    override fun get(): Float
        = intBitsToFloat(bits.get())

    override fun getAndSet(newValue: Float): Float {
        return intBitsToFloat(bits.getAndSet(floatToIntBits(newValue)))
    }

    override fun toString(): String =
        get().toString()

    override fun toByte(): Byte {
        return get().toInt().toByte()
    }

    override fun toDouble(): Double {
        return get().toDouble()
    }

    override fun toFloat(): Float {
        return get()
    }

    override fun toInt(): Int {
        return get().toInt()
    }

    override fun toLong(): Long {
        return get().toLong()
    }

    override fun toShort(): Short {
        return get().toInt().toShort()
    }
}