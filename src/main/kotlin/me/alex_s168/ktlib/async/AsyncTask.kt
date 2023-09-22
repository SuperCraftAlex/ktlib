package me.alex_s168.ktlib.async

/**
 * A simple async task.
 */
class AsyncTask(
    private val task: () -> Unit
) {

    @Volatile
    private var thread: Thread? = Thread(task)

    /**
     * Start this task.
     */
    fun start() =
        thread!!.start()

    /**
     * Wait for this task to finish.
     */
    fun await() =
        thread?.join()

    /**
     * Wait for this task to finish.
     */
    fun await(timeout: Long) =
        thread?.join(timeout)

    /**
     * Check if this task is alive.
     */
    fun isAlive() =
        thread?.isAlive ?: false

    /**
     * Stop this task.
     */
    fun stop() {
        thread = null
    }

    /**
     * Execute a task after this task is finished.
     */
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