package me.alex_s168.ktlib.any

@Deprecated(
    message = "Use println(x) instead!",
    replaceWith = ReplaceWith("println(this)")
)
fun Any.println() = println(this)