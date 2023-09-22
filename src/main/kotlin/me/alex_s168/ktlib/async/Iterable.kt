package me.alex_s168.ktlib.async

import me.alex_s168.ktlib.atomic.inc
import me.alex_s168.ktlib.atomic.num.AtomicInt
import java.util.concurrent.CancellationException
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Executes the given function for each element of this collection asynchronously.
 * @param process the function to execute.
 */
fun <T> Iterable<T>.forEachAsync(
    process: (T) -> Unit
) {
    val tasks = mutableListOf<AsyncTask>()
    forEach {
        tasks += async {
            process(it)
        }
    }
    tasks.await()
}

/**
 * Maps the elements of this collection asynchronously.
 * (Useful for when you don't care about the order of the elements.)
 * @param process the function to execute.
 */
fun <T, E> Iterable<T>.mapAsync(
    process: (T) -> E
): MutableCollection<E> {
    val tasks = mutableListOf<AsyncTask>()
    val result = concurrentMutableCollectionOf<E>()
    forEach {
        tasks += async {
            result += process(it)
        }
    }
    tasks.await()
    return result
}

/**
 * Maps the elements of this collection synchronously.
 * Returns a thread safe mutable list containing the results.
 * @param process the function to execute.
 */
fun <T, E> Iterable<T>.mapToConcurrentList(
    process: (T) -> E
): MutableCollection<E> {
    val result = concurrentMutableListOf<E>()
    forEach {
        result += process(it)
    }
    return result
}

/**
 * Returns the number of elements in this collection.
 */
fun <T> Iterable<T>.count(): Int {
    if (this is Collection) return size
    val count = AtomicInt(0)
    forEachAsync {
        count.inc()
    }
    return count.get()
}

/**
 * Returns a thread safe mutable collection containing all elements of this collection.
 */
fun <T> Iterable<T>.toMutableConcurrentCollection(): MutableCollection<T> {
    val set = concurrentMutableCollectionOf<T>()
    forEachAsync {
        set += it
    }
    return set
}

/**
 * Returns a thread safe mutable list containing all elements of this collection.
 */
fun <T> Iterable<T>.toMutableConcurrentList(): MutableList<T> {
    val list = concurrentMutableListOf<T>()
    forEachAsync {
        list += it
    }
    return list
}

/**
 * Awaits all tasks in the iterable.
 */
fun Iterable<AsyncTask>.await() =
    forEach {
        if (it.isAlive())
            it.await()
    }

/**
 * Cancels all tasks in the iterable.
 */
fun Iterable<AsyncTask>.cancel() =
    forEach {
        if (it.isAlive())
            it.stop()
    }

/**
 * Returns true if any of the tasks in the iterable are running
 */
fun Iterable<AsyncTask>.isRunning(): Boolean {
    forEach {
        if (it.isAlive())
            return true
    }
    return false
}

/**
 * Returns a single future from a collection of futures that joins all the futures
 */
fun <T> Iterable<Future<T>>.joinFutures(): Future<Iterable<T>> =
    object : Future<Iterable<T>> {
        private var cancelled = false

        override fun cancel(mayInterruptIfRunning: Boolean): Boolean {
            cancelled = true
            this@joinFutures.forEach {
                it.cancel(mayInterruptIfRunning)
            }
            return true
        }

        override fun isCancelled(): Boolean {
            if (cancelled) {
                return true
            }
            return this@joinFutures.map {
                it.isCancelled
            }.reduce { acc, b ->
                acc && b
            }
        }

        override fun isDone(): Boolean {
            this@joinFutures.forEach {
                if (!it.isDone) {
                    return false
                }
            }
            return true
        }

        override fun get(): Iterable<T> {
            if (cancelled) {
                throw CancellationException()
            }

            if (!isDone) {
                throw Exception("Cannot get the value of a unfinished future!")
            }

            return try {
                 map {
                    it.get()
                }
            } catch (e: Throwable) {
                throw ExecutionException(e)
            }
        }

        override fun get(timeout: Long, unit: TimeUnit): Iterable<T> {
            var out: Iterable<T>? = null
            var ex: Throwable? = null
            val getTask = async {
                try {
                    out = map {
                        it.get()
                    }
                } catch (e: Throwable) {
                    ex = e
                }
            }
            unit.sleep(timeout)
            if (getTask.isAlive()) {
                throw TimeoutException()
            }
            ex?.let {
                throw ExecutionException(it)
            }
            return out!!
        }

    }

/**
 * Creates a future from the task list and an element
 */
fun <T> Iterable<AsyncTask>.createFuture(element: T): Future<T> =
    this.createFuture { element }

/**
 * Creates a future from the task list and an element provider
 */
fun <T> Iterable<AsyncTask>.createFuture(provider: () -> T): Future<T> =
    object : Future<T> {
        private var cancelled = false

        override fun cancel(mayInterruptIfRunning: Boolean): Boolean {
            cancelled = true
            if (mayInterruptIfRunning) {
                this@createFuture.cancel()
            }
            return true
        }

        override fun isCancelled(): Boolean =
            cancelled

        override fun isDone(): Boolean =
            !this@createFuture.isRunning()

        override fun get(): T {
            if (cancelled) {
                throw CancellationException()
            }

            if (this@createFuture.isRunning()) {
                throw Exception("Cannot get the value of a unfinished future!")
            }

            return provider()
        }

        override fun get(timeout: Long, unit: TimeUnit): T =
            throw UnsupportedOperationException("This feature is not implemented yet!")

    }