package me.alex_s168.ktlib.atomic

interface Atomic<T> {

    fun set(newValue: T)

    fun get(): T

    fun getAndSet(newValue: T): T

}