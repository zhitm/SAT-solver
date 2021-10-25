class Node(clause: Clause, var child1: Node? = null, var child2: Node? = null) {
    var myId = id++



    val value = if (!clause.isEmpty()) clause.varArray.joinToString(" ") else "□"

    fun addChild(node: Node) {

        if (child1 == null) child1 = node else child2 = node
    }

    companion object {
        var id = 0
    }
}