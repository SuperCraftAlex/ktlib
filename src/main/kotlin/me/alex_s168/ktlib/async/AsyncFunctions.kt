package me.alex_s168.ktlib.async

fun async(task: () -> Unit) =
    AsyncTask(task).also {
        it.start()
    }