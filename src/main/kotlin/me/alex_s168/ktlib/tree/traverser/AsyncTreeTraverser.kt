package me.alex_s168.ktlib.tree.traverser

import me.alex_s168.ktlib.async.*
import me.alex_s168.ktlib.tree.Node

/**
 * A implementation of [TreeTraverser] that traverses the tree asynchronously.
 *
 * Might not work for all use cases!
 */
abstract class AsyncTreeTraverser<E>(
    val root: Node<E>
): TreeTraverser<E> {

    private val tasks = concurrentMutableCollectionOf<AsyncTask>()

    abstract override fun process(node: Node<E>): Boolean

    private fun processChildren(node: Node<E>, depth: Int) {
        if (depth >= Config.maxAmountOfAsyncRecursion) {
            node.children.forEach { child ->
                if (process(child)) {
                    processChildren(child, depth + 1)
                }
            }
            return
        }
        tasks += node.children.map { child ->
            async {
                if (process(child)) {
                    processChildren(child, depth + 1)
                }
            }
        }
    }

    override fun traverse() {
        if (process(root)) {
            processChildren(root, 0)
        }
        tasks.await()
    }

    override fun cancel() {
        tasks.cancel()
        tasks.clear()
    }

    companion object {
        /**
         * Creates a new [AsyncTreeTraverser] from the given [root] node and [process] function.
         *
         * @param root The root node of the tree to traverse.
         * @param process The function to process each node.
         */
        @JvmStatic
        fun <T> from(
            root: Node<T>,
            process: (Node<T>, TreeTraverser<T>) -> Boolean
        ): AsyncTreeTraverser<T> =
            object: AsyncTreeTraverser<T>(root) {
                override fun process(node: Node<T>): Boolean =
                    process(node, this)
            }
    }

}