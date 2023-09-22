package me.alex_s168.ktlib.collection

/**
 * Execute the block if the collection is not empty.
 */
fun <T> Collection<T>.ifNotEmpty(block: (Collection<T>) -> Unit): Collection<T> {
    if (this.isNotEmpty()) {
        block(this)
    }

    return this
}

/**
 * Execute the block if the collection is empty.
 */
fun <T> Collection<T>.ifEmpty(block: (Collection<T>) -> Unit): Collection<T> {
    if (this.isEmpty()) {
        block(this)
    }

    return this
}