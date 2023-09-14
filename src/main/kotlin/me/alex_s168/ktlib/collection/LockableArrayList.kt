package me.alex_s168.ktlib.collection

import me.alex_s168.ktlib.Lockable
import me.alex_s168.ktlib.exception.ObjectLockedException

class LockableArrayList<T>(
    initialCapacity: Int = 0
): ArrayList<T>(initialCapacity), Lockable {

    private var locked = false

    override fun lock() {
        locked = true
    }

    override fun isLocked(): Boolean =
        locked

    override fun add(element: T): Boolean {
        if (locked) throw ObjectLockedException()

        return super.add(element)
    }

    override fun add(index: Int, element: T) {
        if (locked) throw ObjectLockedException()

        super.add(index, element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        if (locked) throw ObjectLockedException()

        return super.addAll(elements)
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        if (locked) throw ObjectLockedException()

        return super.addAll(index, elements)
    }

    override fun clear() {
        if (locked) throw ObjectLockedException()

        super.clear()
    }

    override fun remove(element: T): Boolean {
        if (locked) throw ObjectLockedException()

        return super.remove(element)
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        if (locked) throw ObjectLockedException()

        return super.removeAll(elements)
    }

    override fun removeAt(index: Int): T {
        if (locked) throw ObjectLockedException()

        return super.removeAt(index)
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        if (locked) throw ObjectLockedException()

        return super.retainAll(elements)
    }

    override fun set(index: Int, element: T): T {
        if (locked) throw ObjectLockedException()

        return super.set(index, element)
    }

    override fun removeRange(fromIndex: Int, toIndex: Int) {
        if (locked) throw ObjectLockedException()

        super.removeRange(fromIndex, toIndex)
    }

}