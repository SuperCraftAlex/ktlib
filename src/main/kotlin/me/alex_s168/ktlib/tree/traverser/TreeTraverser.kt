package me.alex_s168.ktlib.tree.traverser

import me.alex_s168.ktlib.tree.Node

/**
 * A tree traverser.
 *
 * @param E the type of the value of the nodes.
 */
interface TreeTraverser<E> {

    /**
     * Processes the given node.
     *
     * @param node the node to process.
     * @return whether to continue processing the children of that node.
     */
    fun process(node: Node<E>): Boolean

    /**
     * Cancels the traversal.
     */
    fun cancel()

    /**
     * Processes the children of the given node if the function call to [process] returned true.
     * (Repeats the process for each child.)
     */
    fun traverse()

}