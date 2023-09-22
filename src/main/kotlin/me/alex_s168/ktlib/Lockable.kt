package me.alex_s168.ktlib

/**
 * Lockable interface
 */
interface Lockable {

    /**
     * Once this method is called, the object is locked and cannot be unlocked anymore.
     * The state of the object is now unchangeable.
     */
    fun lock()

    /**
     * Returns true if the object is locked, false otherwise.
     */
    fun isLocked(): Boolean

}