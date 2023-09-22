package me.alex_s168.ktlib.async

/**
 * Creates and executes a new [AsyncTask] with the given method as its body.
 */
fun async(task: () -> Unit) =
    AsyncTask(task).also {
        it.start()
    }