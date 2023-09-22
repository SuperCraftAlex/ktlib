package me.alex_s168.ktlib.tree

import me.alex_s168.ktlib.async.mapAsync
import me.alex_s168.ktlib.async.toMutableConcurrentCollection

/**
 * A node in a tree.
 *
 * @param E the type of the value of this node.
 * @property value the value of this node (optional).
 * @property children the children of this node.
 * @property parent the parent of this node (optional).
 */
open class Node<E>(
    open val value: E?,
    open val children: Collection<Node<E>>,
    open val parent: Node<E>?
): Cloneable {

    /**
     * Clones this node.
     * The cloned node will be almost completely independent of this node.
     * The value of the node will NOT be cloned.
     * @return the cloned node.
     */
    public override fun clone(): MutableNode<E> {
        val children = children.map { it.clone() }
        return MutableNode(
            value,
            children.toMutableConcurrentCollection(),
            parent
        )
    }

    /**
     * Clones this node asynchronously.
     * The order will not be preserved.
     * The cloned node will be almost completely independent of this node.
     * The value of the node will NOT be cloned.
     * @return the cloned node.
     */
    fun cloneIgnoreOrder(): MutableNode<E> {
        val children = children.mapAsync { it.cloneIgnoreOrder() }
        return MutableNode(
            value,
            children,
            parent
        )
    }

    /**
     * Outputs this node's value and children as a string.
     */
    override fun toString(): String {
        val sb = StringBuilder("Node: $value")
        for (child in children) {
            val s = child.toString().lines().joinToString("\n") {
                "  $it"
            }
            sb.append("\n-$s")
        }
        return sb.toString()
    }

}