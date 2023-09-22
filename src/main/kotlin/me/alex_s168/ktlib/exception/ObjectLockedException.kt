package me.alex_s168.ktlib.exception

/**
 * Exception thrown when trying to modify a locked object.
 * @see me.alex_s168.ktlib.Lockable
 */
class ObjectLockedException:
    Exception("Cannot modify a locked object!")