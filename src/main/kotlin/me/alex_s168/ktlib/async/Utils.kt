package me.alex_s168.ktlib.async

fun Collection<AsyncTask>.await() {
    for (task in this) {
        if (task.isAlive())
            task.await()
    }
}