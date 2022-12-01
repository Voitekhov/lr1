fun main() {

    InputUtils.readData()
    val root = Node(InputData.asIs, -1.0, Solution.calculateDifferance(InputData.asIs), null)
    println(Solution.nodeToString(root))
    Solution.populateChildren(root)
    Solution.deleteUsedNode(root)

    var minNode = Solution.getMinFNode()

    // tree walk
    var G = minNode.g
    while (G != 0.0) {
        Solution.populateChildren(minNode)
        Solution.deleteUsedNode(minNode)
        minNode = Solution.getMinFNode()
        G = minNode.g
    }

    println("Height: ${Solution.getRealHeight(minNode)}")
    println("Total matrix: ${Solution.counter}")
    println(
        "${Solution.getRealHeight(minNode)} / ${Solution.counter} = ${
            (Solution.getRealHeight(minNode).toDouble() / Solution.counter.toDouble())
        }"
    )
    Solution.printWay(minNode)
}


class Solution {
    companion object {
        // used it to not write algorithm of tree walk
        var counter = 0;
        val usedMatrix = arrayListOf<Array<Array<Int>>>()

        // used it to not write algorithm of tree walk
        val allNodes = arrayListOf<Node>()

        fun getMinFNode(): Node {
            allNodes.reverse()
            val node = allNodes.stream().min { o1, o2 -> o1.getF().compareTo(o2.getF()) }.get()
            allNodes.reverse()
            return node
        }

        fun deleteUsedNode(node: Node) {
            allNodes.remove(node)
            addToUsedMatrix(node.arr)
        }

        fun addToUsedMatrix(matrix: Array<Array<Int>>) {
            usedMatrix.add(matrix)
        }

        fun populateChildren(node: Node) {
            val emptyPosition = findEmptyCell(node)
            val positions = getVariantsForPast(emptyPosition)
            val printNodes: MutableList<Node> = mutableListOf();
            for (position in positions) {
                makeNewNode(node, position, emptyPosition, positions.size, printNodes)
            }

            for(node in printNodes){
                var row = node.arr[0]
                for(el in row){
                    print("$el ")
                }
                print("                    ")
            }
            println()
            for(node in printNodes){
                var row = node.arr[1]
                for(el in row){
                    print("$el ")
                }
                print("                    ")
            }
            println()
            for(node in printNodes){
                var row = node.arr[2]
                for(el in row){
                    print("$el ")
                }
                print("                    ")

            }
            println()
            for (node in printNodes){
                print("G=${node.g} H=${node.h}")
                print("               ")
            }
            println()
            println()
        }

        fun findEmptyCell(node: Node): Pair<Int, Int> {
            for (i in 0 until node.arr.size) {
                val row = node.arr[i]
                for (j in row.indices) {
                    if (node.arr[i][j] == 0) {
                        return Pair(i, j)
                    }
                }
            }
            return Pair(-1, -1)
        }

        fun getVariantsForPast(emptyPositionIndex: Pair<Int, Int>): List<Pair<Int, Int>> {
            val result = arrayListOf<Pair<Int, Int>>()

            var upPosition = Pair(emptyPositionIndex.first - 1, emptyPositionIndex.second)
            var downPosition = Pair(emptyPositionIndex.first + 1, emptyPositionIndex.second)
            var leftPosition = Pair(emptyPositionIndex.first, emptyPositionIndex.second - 1)
            var rightPosition = Pair(emptyPositionIndex.first, emptyPositionIndex.second + 1)

            val positions = arrayListOf(leftPosition, upPosition, rightPosition, downPosition)

            for (position in positions) {
                if (position.first < 3 && position.first >= 0 && position.second < 3 && position.second >= 0) {
                    result.add(position)
                }
            }
            return result
        }

        fun makeNewNode(node: Node, position: Pair<Int, Int>, emptyPosition: Pair<Int, Int>, countOfChildren: Int, printNode: MutableList<Node>) {
            if (countOfChildren == node.getChildrenCount()) {
                return
            }
            val newArr: Array<Array<Int>> = Array(3, { Array(3, { 0 }) })
            // create full copy
            for (i in 0 until node.arr.size) {
                val row = node.arr[i]
                for (j in row.indices) {
                    newArr[i][j] = node.arr[i][j]
                }
            }
            // change position
            newArr[emptyPosition.first][emptyPosition.second] = newArr[position.first][position.second]
            newArr[position.first][position.second] = 0
            if (isUsedMatrix(newArr)) {
                return
            }
            // calculate differance
            val g = calculateDifferance(newArr) * InputData.G
            val h = (node.h + 1) * InputData.H
            // populate child
            // TODO better use list of nodes in Node.class
            if (node.first == null) {
                node.first = Node(newArr, h, g, node)
                allNodes.add(node.first!!)
                counter++
                printNode.add(node.first!!)
                return
            }
            if (node.second == null) {
                node.second = Node(newArr, h, g, node)
                allNodes.add(node.second!!)
                counter++
                printNode.add(node.second!!)
                return
            }
            if (node.third == null) {
                node.third = Node(newArr, h, g, node)
                allNodes.add(node.third!!)
                counter++
                printNode.add(node.third!!)
                return
            }
            if (node.fourth == null) {
                node.fourth = Node(newArr, h, g, node)
                allNodes.add(node.fourth!!)
                counter++
                printNode.add(node.fourth!!)
                return
            }
        }

        fun calculateDifferance(newArr: Array<Array<Int>>): Double {
            var g = 0
            for (i in 0 until InputData.toBe.size) {
                val row = InputData.toBe[i]
                for (j in row.indices) {
                    if (!InputData.toBe[i][j].equals(newArr[i][j])) {
                        g++
                    }
                }
            }
            return g.toDouble()

        }

        fun getRealHeight(node: Node?, counter: Int = 0): Int {
            if (node!!.parent != null) {
                val tmp = counter + 1
                return getRealHeight(node.parent, tmp)
            }
            return counter
        }

        fun isUsedMatrix(matrix: Array<Array<Int>>): Boolean {
            var counter = 0;
            for (used in usedMatrix) {
                for (i in 0 until used.size) {
                    val row = used[i]
                    for (j in row.indices) {
                        if (used[i][j].equals(matrix[i][j])) {
                            counter++
                        }
                    }
                }
                if (counter == 9) {
                    return true
                }
                counter = 0
            }
            return false
        }

        fun printWay(node: Node?) {
            if (node != null) {
                printWay(node.parent)
                println()
                printArr(node.arr)
                if (node.first != null) {
                    println(
                        " || \n \\/ "
                    )
                }
            }

            return
        }

        private fun printArr(arr: Array<Array<Int>>) {
            for (row in arr) {
                for (number in row) {
                    print("$number ")
                }
                println()
            }
        }

         fun nodeToString(node: Node): String {
            val arr = node.arr
            val result = StringBuilder()
            for (row in arr) {
                for (number in row) {
                    result.append("$number ")
                }
                result.append("\n")
            }
            result.append("G=${node.g} H=${node.h} \n")
            return result.toString()
        }

    }


}

class InputData {
    companion object {
        val asIs: Array<Array<Int>> = Array(3) { Array(3) { 0 } }
        val toBe: Array<Array<Int>> = Array(3) { Array(3) { 0 } }
        var H = 1
        var G = 1
    }
}