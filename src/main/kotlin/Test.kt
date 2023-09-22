import me.alex_s168.ktlib.tree.MutableTree

fun main() {

    val tree = MutableTree.create<String>()

    tree += "aa"

    val tree2 = tree.mutateIgnoreOrder()

    tree2.root.children.first() += "bbb"

    tree2 += "ccc"
    tree2 += "gg"

    val tree3 = tree2.cloneIgnoreOrder()

    println(tree3)

}