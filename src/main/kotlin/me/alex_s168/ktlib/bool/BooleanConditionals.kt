package me.alex_s168.ktlib.bool

fun Boolean.then(block: () -> Unit): Boolean {
    if (this) {
        block()
    }

    return this
}

fun Boolean.otherwise(block: () -> Unit): Boolean {
    if (!this) {
        block()
    }

    return this
}

fun Boolean.then(block: () -> Unit, otherwise: () -> Unit): Boolean {
    if (this) {
        block()
    } else {
        otherwise()
    }

    return this
}