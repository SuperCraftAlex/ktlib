package me.alex_s168.ktlib.tree.traverser

import me.alex_s168.ktlib.async.AsyncTask
import me.alex_s168.ktlib.async.async
import me.alex_s168.ktlib.async.await
import me.alex_s168.ktlib.async.cancel
import me.alex_s168.ktlib.tree.Node

/**
 * A implementation of [TreeTraverser] that traverses the tree asynchronously.
 *
 * Might not work for all use cases!
 */
abstract class AsyncTreeTraverser<E>(
    val root: Node<E>
): TreeTraverser<E> {

    private val tasks = mutableListOf<AsyncTask>()

    abstract override fun process(node: Node<E>): Boolean

    private fun processChildren(node: Node<E>) {
        tasks += node.children.map { child ->
            async {
                if (process(child)) {
                    processChildren(child)
                }
            }
        }
    }

    override fun traverse() {
        if (process(root)) {
            processChildren(root)
        }
        tasks.await()
    }

    override fun cancel() {
        tasks.cancel()
        tasks.clear()
    }

    companion object {
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