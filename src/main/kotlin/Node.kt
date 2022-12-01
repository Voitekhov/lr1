class Node(
    val arr: Array<Array<Int>> = Array(3, { Array(3, { 0 }) }),
    var h: Double = 0.0,
    var g: Double = 0.0,
    var parent: Node? = null,
    var first: Node? = null,
    var second: Node? = null,
    var third: Node? = null,
    var fourth: Node? = null
) {

    // TODO transform to property
    fun getF(): Double {
        return h + g;
    }

    // TODO transform to property
    fun getChildrenCount(): Int {
        var counter = 0;
        if (first != null) {
            counter++
        }
        if (second != null) {
            counter++
        }
        if (third != null) {
            counter++
        }
        if (fourth != null) {
            counter++
        }
        return counter;
    }


}