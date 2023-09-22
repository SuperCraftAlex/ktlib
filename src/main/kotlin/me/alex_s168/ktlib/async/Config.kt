package me.alex_s168.ktlib.async

object Config {

    /**
     * How deep asynchronous recursion can go.
     * After this it will continue synchronously.
     */
    var maxAmountOfAsyncRecursion = 3

    /**
     * How many threads the asynchronous forEach and map functions can spawn.
     */
    var maxAmountOfAsyncThreadsInMap = 4

}