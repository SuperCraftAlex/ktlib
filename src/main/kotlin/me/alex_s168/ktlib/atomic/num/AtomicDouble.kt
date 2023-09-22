package me.alex_s168.ktlib.atomic.num

import java.lang.Double.doubleToLongBits
import java.lang.Double.longBitsToDouble
import java.util.concurrent.atomic.AtomicLong

class AtomicDouble(
    initialValue: Double = 0.0
): AtomicNum<Double>() {

    private val bits: AtomicLong =
        AtomicLong(doubleToLongBits(initialValue))

    override fun set(newValue: Double) {
        bits.set(doubleToLongBits(newValue))
    }

    override fun addNew(value: Double): Double =
        get() + value

    override fun subNew(value: Double): Double =
        get() - value

    override fun mulNew(value: Double): Double =
        get() * value

    override fun divNew(value: Double): Double =
        get() / value

    override fun remNew(value: Double): Double =
        get() % value

    override fun one(): Double =
        1.0

    override fun get(): Double
        = longBitsToDouble(bits.get())

    override fun getAndSet(newValue: Double): Double {
        return longBitsToDouble(bits.getAndSet(doubleToLongBits(newValue)))
    }

    override fun toString(): String =
        get().toString()

    override fun toByte(): Byte {
        return get().toInt().toByte()
    }

    override fun toDouble(): Double {
        return get()
    }

    override fun toFloat(): Float {
        return get().toFloat()
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