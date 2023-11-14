package me.alex_s168.ktlib.async

import me.alex_s168.ktlib.any.ignore

fun Iterable<AsyncTask>.asRunning(): Running {
    return object : Running {
        override fun await() =
            forEach {
                if (it.isAlive())
                    it.await()
            }

        override fun cancel() =
            forEach {
                if (it.isAlive())
                    it.stop()
            }

        override fun isRunning(): Boolean {
            forEach {
                if (it.isAlive())
                    return true
            }
            return false
        }
    }
}

fun Running.then(other: Runnable): Running =
    object : Running {
        val task = async {
            await()
            other.run()
        }

        override fun await() {
            task.await()
        }

        override fun cancel() {
            task.stop()
        }

        override fun isRunning(): Boolean {
            return task.isAlive()
        }
    }

fun Running.then(other: () -> Unit): Running =
    this.then(Runnable(other))