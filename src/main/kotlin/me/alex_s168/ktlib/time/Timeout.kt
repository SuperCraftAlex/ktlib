package me.alex_s168.ktlib.time

import me.alex_s168.ktlib.async.async
import java.util.concurrent.TimeUnit

/**
 * Executes the given block in a different thread and waits for the given timeout.
 */
fun withTimeout(timeout: Long, unit: TimeUnit, block: () -> Unit) {
    val task = async {
        block()
    }
    unit.sleep(timeout)
    if (task.isAlive()) {
        task.stop()
    }
}