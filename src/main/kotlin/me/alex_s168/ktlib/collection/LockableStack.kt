package me.alex_s168.ktlib.collection

import me.alex_s168.ktlib.Lockable
import me.alex_s168.ktlib.exception.ObjectLockedException
import java.util.Comparator
import java.util.Stack

/**
 * A [Stack] that can be locked to prevent further modification.
 * @see Lockable
 */
class LockableStack<T>: Stack<T>(), Lockable {

    private var locked = false

    override fun lock() {
        locked = true
    }

    override fun isLocked(): Boolean =
        locked

    override fun pop(): T {
        if (locked) throw ObjectLockedException()

        return super.pop()
    }

    override fun setElementAt(obj: T, index: Int) {
        if (locked) throw ObjectLockedException()

        super.setElementAt(obj, index)
    }

    override fun addElement(obj: T) {
        if (locked) throw ObjectLockedException()

        super.addElement(obj)
    }

    override fun insertElementAt(obj: T, index: Int) {
        if (locked) throw ObjectLockedException()

        super.insertElementAt(obj, index)
    }

    override fun removeElementAt(index: Int) {
        if (locked) throw ObjectLockedException()

        super.removeElementAt(index)
    }

    override fun removeAllElements() {
        if (locked) throw ObjectLockedException()

        super.removeAllElements()
    }

    override fun removeElement(obj: Any?): Boolean {
        if (locked) throw ObjectLockedException()

        return super.removeElement(obj)
    }

    override fun push(obj: T): T {
        if (locked) throw ObjectLockedException()

        return super.push(obj)
    }

    override fun set(index: Int, element: T): T {
        if (locked) throw ObjectLockedException()

        return super.set(index, element)
    }

    override fun add(index: Int, element: T) {
        if (locked) throw ObjectLockedException()

        super.add(index, element)
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        if (locked) throw ObjectLockedException()

        return super.addAll(index, elements)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        if (locked) throw ObjectLockedException()

        return super.addAll(elements)
    }

    override fun removeRange(fromIndex: Int, toIndex: Int) {
        if (locked) throw ObjectLockedException()

        super.removeRange(fromIndex, toIndex)
    }

    override fun clear() {
        if (locked) throw ObjectLockedException()

        super.clear()
    }

    override fun sort(c: Comparator<in T>?) {
        if (locked) throw ObjectLockedException()

        super.sort(c)
    }

}