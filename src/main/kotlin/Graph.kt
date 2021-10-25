import java.io.File

class Graph() {
    private val nodes = mutableListOf<Node>()
    private val edges = mutableListOf<Pair<Node,Node>>()

    fun addEdge(first: Node, second: Node) {
        if (first !in nodes) nodes.add(first)
        if (second !in nodes) nodes.add(second)
        edges.add(Pair(first, second))
    }

    fun getNode(clause: Clause):Node{
        for (node in nodes) {
            if (node.value==clause.varArray.joinToString(" ")) return node
        }
        return Node(clause)
    }

    fun createGraphvizFile(){
        File("src/main/dot/somefile.dot").printWriter().use { out -> out.println("digraph G {")
            edges.forEach {
                out.println("${it.second.myId} -> ${it.first.myId};")
            }
            nodes.forEach{ out.println("${it.myId} [label=\"${it.value}\"] ")}
            out.println("}")
        }
    }}