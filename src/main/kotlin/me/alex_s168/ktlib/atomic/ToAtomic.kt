package me.alex_s168.ktlib.atomic

import me.alex_s168.ktlib.atomic.num.AtomicFloat
import me.alex_s168.ktlib.atomic.num.AtomicInt
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference

fun <T> T.toAtomicRef() = AtomicReference(this)

fun Int.toAtomic() = AtomicInt(this)

fun Long.toAtomic() = AtomicLong(this)

fun Boolean.toAtomic() = AtomicBoolean(this)

fun Float.toAtomic() = AtomicFloat(this)