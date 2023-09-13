package me.alex_s168.ktlib.async

class AsyncTask(
    private val task: () -> Unit
) {

    @Volatile
    private var thread: Thread? = Thread(task)

    fun start() =
        thread!!.start()

    fun await() =
        thread?.join()

    fun await(timeout: Long) =
        thread?.join(timeout)

    fun isAlive() =
        thread?.isAlive ?: false

    fun stop() {
        thread = null
    }

    fun then(task: () -> Unit): AsyncTask {
        async {
            while (isAlive()) {
                Thread.sleep(1)
            }
            task()
        }

        return this
    }

}