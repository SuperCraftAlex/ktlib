package me.alex_s168.ktlib.atomic.num

import me.alex_s168.ktlib.atomic.Atomic

abstract class AtomicNum<T>: Atomic<T>, Number() {

    abstract fun addNew(value: T): T

    abstract fun subNew(value: T): T

    abstract fun mulNew(value: T): T

    abstract fun divNew(value: T): T

    abstract fun remNew(value: T): T

    abstract fun one(): T

}