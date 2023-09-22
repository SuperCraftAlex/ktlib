package me.alex_s168.ktlib.tree

import me.alex_s168.ktlib.async.*
import me.alex_s168.ktlib.atomic.inc
import me.alex_s168.ktlib.atomic.toAtomic
import me.alex_s168.ktlib.tree.traverser.AsyncTreeTraverser
import me.alex_s168.ktlib.tree.traverser.SimpleTreeTraverser
import me.alex_s168.ktlib.tree.traverser.TreeTraverser

/**
 * A tree.
 * @param E the type of the value of this tree.
 * @property root the root node of this tree.
 */
open class Tree<E>(
    open val root: Node<E>
): Collection<E>, Cloneable {

    /**
     * Returns true if the values of the tree
     */
    fun isAvailable(): Boolean =
        root.isAvailable()

    /**
     * Waits for the values to be available
     */
    fun await() =
        root.await()

    /**
     * Outputs this tree as a string
     */
    override fun toString(): String =
        root.toString()

    /**
     * Completely clones this tree.
     * The cloned tree will be almost completely independent of this tree.
     * The values of the nodes will NOT be cloned.
     * @return the cloned tree.
     */
    public override fun clone(): Tree<E> =
        Tree(root.clone())

    /**
     * Completely clones this tree asynchronously.
     * The order will not be preserved.
     * The cloned tree will be almost completely independent of this tree.
     * The values of the nodes will NOT be cloned.
     * @see clone
     */
    fun cloneIgnoreOrder(): Tree<E> =
        Tree(root.cloneIgnoreOrder())

    /**
     * Clones this tree to a mutable tree.
     */
    fun mutate(): MutableTree<E> =
        MutableTree(root.clone())

    /**
     * Clones this tree to a mutable tree asynchronously.
     * The order will not be preserved.
     */
    fun mutateIgnoreOrder(): MutableTree<E> =
        MutableTree(root.cloneIgnoreOrder())

    /**
     * Traverses the tree synchronously.
     * It is slower than [traverseAsync] but it is guaranteed that the tree will be traversed in order.
     * If you want to traverse the tree asynchronously, use [traverseAsync] instead.
     * @see traverseAsync
     * @param process the process to execute on each node. If it returns true, the node's children will be traversed.
     */
    fun traverse(
        process: (Node<E>, TreeTraverser<E>) -> Boolean
    ) {
        // Uses SimpleTreeTraverser to traverse the tree synchronously.

        val traverser = SimpleTreeTraverser.from(root, process)
        traverser.traverse()
    }

    /**
     * Traverses the tree asynchronously.
     * It is faster than [traverse] but it is not guaranteed that the tree will be traversed in order.
     * This might be a problem if you want to traverse the tree in order.
     * If you want to traverse the tree in order, use [traverse] instead.
     * @see traverse
     * @param process the process to execute on each node. If it returns true, the node's children will be traversed.
     */
    fun traverseAsync(
        process: (Node<E>, TreeTraverser<E>) -> Boolean
    ) {
        // Uses AsyncTreeTraverser to traverse the tree asynchronously.

        val traverser = AsyncTreeTraverser.from(root, process)
        traverser.traverse()
    }

    /**
     * The amount of values in the tree.
     */
    override val size: Int
        get() {
            // Uses AsyncTreeTraverser to traverse the tree asynchronously.

            val size = 0.toAtomic()
            val traverser = AsyncTreeTraverser.from(root) { node, _ ->
                node.value?.let { size.inc() }
                return@from true
            }
            traverser.traverse()
            return size.get()
        }

    /**
     * Checks if the tree contains the specified value.
     */
    override fun contains(element: E): Boolean {
        // Uses AsyncTreeTraverser to traverse the tree asynchronously.

        val found = false.toAtomic()
        val traverser = AsyncTreeTraverser.from(root) { node, traverser ->
            if (node.value == element) {
                found.set(true)
                traverser.cancel()
                return@from false
            }
            return@from true
        }
        traverser.traverse()
        return found.get()
    }

    /**
     * Checks if the tree contains all the specified values.
     */
    override fun containsAll(elements: Collection<E>): Boolean {
        // Uses [contains] asynchronously to check if all elements are contained in the tree.

        val tasks = mutableListOf<AsyncTask>()
        val found = true.toAtomic()

        for (element in elements) {
            tasks += async {
                if (!contains(element)) {
                    found.set(false)
                    tasks.cancel()
                }
            }
        }

        tasks.await()

        return found.get()
    }

    /**
     * Checks if the values of the tree are empty.
     */
    override fun isEmpty(): Boolean =
        root.value == null && root.children.isEmpty() || size == 0

    /**
     * Returns a collection of all the values in the tree.
     */
    val values: Collection<E>
        get() = getAllValues()

    private fun getAllValues(): Collection<E> {
        // Uses AsyncTreeTraverser to traverse the tree asynchronously.

        val values = concurrentMutableCollectionOf<E>()
        val traverser = AsyncTreeTraverser.from(root) { node, _ ->
            node.value?.let { values += it }
            return@from true
        }
        traverser.traverse()
        return values
    }

    /**
     * Returns an iterator over all the values in the tree.
     * @return the iterator.
     */
    override fun iterator(): Iterator<E> =
        values.iterator()

}