package me.alex_s168.ktlib.async

interface Running {
    fun await()

    fun cancel()

    fun isRunning(): Boolean
}