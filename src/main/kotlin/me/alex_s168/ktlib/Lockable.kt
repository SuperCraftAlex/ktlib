package me.alex_s168.ktlib

interface Lockable {

    fun lock()

    fun isLocked(): Boolean

}