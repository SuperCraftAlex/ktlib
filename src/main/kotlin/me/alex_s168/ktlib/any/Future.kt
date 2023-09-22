package me.alex_s168.ktlib.any

import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

/**
 * Returns a future that always has the value available
 */
fun <T> T.toFuture(): Future<T> =
    object : Future<T> {
        override fun cancel(mayInterruptIfRunning: Boolean): Boolean =
            false

        override fun isCancelled(): Boolean =
            false

        override fun isDone(): Boolean =
            true

        override fun get(): T =
            this@toFuture

        override fun get(timeout: Long, unit: TimeUnit): T =
            this@toFuture

    }