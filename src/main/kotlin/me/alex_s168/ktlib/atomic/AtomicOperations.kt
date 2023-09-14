package me.alex_s168.ktlib.atomic

import me.alex_s168.ktlib.atomic.num.AtomicNum

operator fun <T: AtomicNum<E>, E> T.plus(value: E): E =
    addNew(value)

operator fun <T: AtomicNum<E>, E> T.minus(value: E): E =
    subNew(value)

operator fun <T: AtomicNum<E>, E> T.times(value: E): E =
    mulNew(value)

operator fun <T: AtomicNum<E>, E> T.div(value: E): E =
    divNew(value)

operator fun <T: AtomicNum<E>, E> T.rem(value: E): E =
    remNew(value)

operator fun <T: AtomicNum<E>, E> T.plusAssign(value: E) {
    set(addNew(value))
}

operator fun <T: AtomicNum<E>, E> T.minusAssign(value: E) {
    set(subNew(value))
}

operator fun <T: AtomicNum<E>, E> T.timesAssign(value: E) {
    set(mulNew(value))
}

operator fun <T: AtomicNum<E>, E> T.divAssign(value: E) {
    set(divNew(value))
}

operator fun <T: AtomicNum<E>, E> T.remAssign(value: E) {
    set(remNew(value))
}

fun <T: AtomicNum<E>, E> T.getAndAdd(value: E): E =
    get().also {
        set(addNew(value))
    }

fun <T: AtomicNum<E>, E> T.getAndSub(value: E): E =
    get().also {
        set(subNew(value))
    }

fun <T: AtomicNum<E>, E> T.getAndMul(value: E): E =
    get().also {
        set(mulNew(value))
    }

fun <T: AtomicNum<E>, E> T.getAndDiv(value: E): E =
    get().also {
        set(divNew(value))
    }

fun <T: AtomicNum<E>, E> T.getAndRem(value: E): E =
    get().also {
        set(remNew(value))
    }

fun <T: AtomicNum<E>, E> T.addAndGet(value: E): E =
    addNew(value).also {
        set(it)
    }

fun <T: AtomicNum<E>, E> T.subAndGet(value: E): E =
    subNew(value).also {
        set(it)
    }

fun <T: AtomicNum<E>, E> T.mulAndGet(value: E): E =
    mulNew(value).also {
        set(it)
    }

fun <T: AtomicNum<E>, E> T.divAndGet(value: E): E =
    divNew(value).also {
        set(it)
    }

fun <T: AtomicNum<E>, E> T.remAndGet(value: E): E =
    remNew(value).also {
        set(it)
    }

fun <T: AtomicNum<E>, E> T.incAndGet(): E =
    addAndGet(one())

fun <T: AtomicNum<E>, E> T.decAndGet(): E =
    subAndGet(one())

fun <T: AtomicNum<E>, E> T.getAndInc(): E =
    getAndAdd(one())

fun <T: AtomicNum<E>, E> T.getAndDec(): E =
    getAndSub(one())

fun <T: AtomicNum<E>, E> T.inc() =
    set(addNew(one()))

fun <T: AtomicNum<E>, E> T.dec() =
    set(subNew(one()))