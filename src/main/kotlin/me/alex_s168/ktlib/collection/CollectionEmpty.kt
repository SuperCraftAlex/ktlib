package me.alex_s168.ktlib.collection

fun <T> Collection<T>.ifNotEmpty(block: (Collection<T>) -> Unit): Collection<T> {
    if (this.isNotEmpty()) {
        block(this)
    }

    return this
}

fun <T> Collection<T>.ifEmpty(block: (Collection<T>) -> Unit): Collection<T> {
    if (this.isEmpty()) {
        block(this)
    }

    return this
}