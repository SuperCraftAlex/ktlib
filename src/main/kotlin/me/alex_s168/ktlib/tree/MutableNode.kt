package me.alex_s168.ktlib.tree

import me.alex_s168.ktlib.async.concurrentMutableListOf

/**
 * A node in a tree.
 * Like [Node], but with mutable properties.
 *
 * @param E the type of the value of this node.
 * @property value the value of this node (optional).
 * @property children the children of this node.
 * @property parent the parent of this node (optional).
 */
class MutableNode<E>(
    override var value: E?,
    override val children: MutableCollection<MutableNode<E>>,
    override var parent: Node<E>?
): Node<E>(
    value,
    children,
    parent
) {

    operator fun plusAssign(value: E) {
        children += MutableNode(
            value = value,
            children = concurrentMutableListOf(),
            this
        )
    }

}