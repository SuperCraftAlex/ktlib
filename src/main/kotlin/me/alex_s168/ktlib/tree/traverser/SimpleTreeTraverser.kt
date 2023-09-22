package me.alex_s168.ktlib.tree.traverser

import me.alex_s168.ktlib.tree.Node

/**
 * An implementation of [TreeTraverser] that traverses the tree synchronously.
 */
abstract class SimpleTreeTraverser<T>(
    val root: Node<T>
): TreeTraverser<T> {
    private var cancelled = false

    abstract override fun process(node: Node<T>): Boolean

    override fun cancel() {
        cancelled = true
    }

    private fun traverse(node: Node<T>) {
        if (cancelled) return
        if (process(node)) {
            for (child in node.children) {
                traverse(child)
            }
        }
    }

    override fun traverse() =
        traverse(root)

    companion object {
        @JvmStatic
        fun <T> from(
            root: Node<T>,
            process: (Node<T>, TreeTraverser<T>) -> Boolean
        ): SimpleTreeTraverser<T> =
            object: SimpleTreeTraverser<T>(root) {
                override fun process(node: Node<T>): Boolean =
                    process(node, this)
            }
    }
}