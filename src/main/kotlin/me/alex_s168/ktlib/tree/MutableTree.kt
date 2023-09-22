package me.alex_s168.ktlib.tree

import me.alex_s168.ktlib.async.concurrentMutableListOf
import me.alex_s168.ktlib.async.forEachAsync
import me.alex_s168.ktlib.async.toMutableConcurrentCollection
import me.alex_s168.ktlib.atomic.toAtomic
import me.alex_s168.ktlib.tree.traverser.AsyncTreeTraverser

/**
 * A mutable tree.
 * @see Tree
 * @param E the type of the value of this tree.
 * @property root the root node of this tree.
 */
class MutableTree<E>(
    override val root: MutableNode<E>
): Tree<E>(
    root
), MutableCollection<E> {

    operator fun plusAssign(element: MutableNode<E>) {
        root.children.add(element)
    }

    /**
     * Adds an element to the children of the root node.
     */
    override fun add(element: E): Boolean {
        root.children.add(MutableNode(element, concurrentMutableListOf(), null))
        return true
    }

    /**
     * Adds all elements of the specified collection to the children of the root node.
     */
    override fun addAll(elements: Collection<E>): Boolean {
        elements.forEach {
            add(it)
        }
        return true
    }

    /**
     * Adds all elements of the specified collection to the children of the root node.
     * Does not preserve order.
     */
    fun addAllIgnoreOrder(elements: Collection<E>): Boolean {
        elements.forEachAsync {
            add(it)
        }
        return true
    }

    /**
     * Clears the root node.
     */
    override fun clear() {
        root.children.clear()
        root.value = null
    }

    // TODO: make iterator be linked to a tree traverser
    /**
     * Returns an iterator over the elements of the tree.
     * Removing elements from the iterator will NOT remove them from the tree.
     */
    @Deprecated(
        message = "This method is not fully implemented yet.",
        level = DeprecationLevel.WARNING
    )
    override fun iterator(): MutableIterator<E> {
        // Uses AsyncTreeTraverser to traverse the tree asynchronously.

        val values = concurrentMutableListOf<E>()
        val traverser = AsyncTreeTraverser.from(root) { node, _ ->
            node.value?.let { values += it }
            return@from true
        }
        traverser.traverse()
        return values.iterator()
    }

    /**
     * Retains only the elements of the tree that are contained in the specified collection.
     * @return true if the tree was modified as a result of this operation.
     */
    override fun retainAll(elements: Collection<E>): Boolean {
        // Uses AsyncTreeTraverser to traverse the tree asynchronously.

        val left = elements.toMutableConcurrentCollection()

        val modified = false.toAtomic()
        val traverser = AsyncTreeTraverser.from(root) { node, traverser ->
            node as MutableNode
            if (node.value !in elements) {
                modified.set(true)
                left.remove(node.value)
                node.value = null
                if (left.isEmpty()) {
                    traverser.cancel()
                }
                return@from false
            }
            return@from true
        }
        traverser.traverse()
        return modified.get()
    }

    /**
     * Removes all elements from the tree.
     * @return true if the tree was modified as a result of this operation.
     */
    override fun removeAll(elements: Collection<E>): Boolean {
        // Uses AsyncTreeTraverser to traverse the tree asynchronously.

        val left = elements.toMutableConcurrentCollection()

        val modified = false.toAtomic()
        val traverser = AsyncTreeTraverser.from(root) { node, traverser ->
            node as MutableNode
            if (node.value in elements) {
                modified.set(true)
                left.remove(node.value)
                node.value = null
                if (left.isEmpty()) {
                    traverser.cancel()
                }
                return@from false
            }
            return@from true
        }
        traverser.traverse()
        return modified.get()
    }

    /**
     * Removes the specified element from the tree.
     * @return true if the tree was modified as a result of this operation.
     */
    override fun remove(element: E): Boolean {
        // Uses AsyncTreeTraverser to traverse the tree asynchronously.

        val found = false.toAtomic()
        val traverser = AsyncTreeTraverser.from(root) { node, traverser ->
            node as MutableNode
            if (node.value == element) {
                found.set(true)
                node.value = null
                traverser.cancel()
                return@from false
            }
            return@from true
        }
        traverser.traverse()
        return found.get()
    }

    companion object {
        /**
         * Creates a new MutableTree with the specified root value (optional).
         */
        @JvmStatic
        fun <E> create(rootValue: E? = null): MutableTree<E> {
            return MutableTree(
                MutableNode(
                    value = rootValue,
                    children = concurrentMutableListOf(),
                    parent = null
                )
            )
        }
    }

}