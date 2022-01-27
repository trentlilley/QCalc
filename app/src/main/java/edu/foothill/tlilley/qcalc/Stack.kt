package edu.foothill.tlilley.qcalc

class Stack: Iterable<Any> {
    val stack: MutableList<Any> = mutableListOf()

    fun isEmpty() = stack.isEmpty()

    fun size() = stack.size

    fun push(element: Any) = stack.add(element)

    fun pop(): Any {
        val popped = stack.last()
        if (!isEmpty()) {
            stack.removeAt(stack.size - 1)
        }
        return popped
    }

    fun peek(): Any? = stack.lastOrNull()

    override fun toString(): String = stack.toString()

    override fun iterator(): Iterator<Any> {
        return stack.iterator()
    }
}