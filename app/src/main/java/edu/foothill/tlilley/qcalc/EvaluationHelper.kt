package edu.foothill.tlilley.qcalc

import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.StringBuilder
import kotlin.math.pow

@RequiresApi(Build.VERSION_CODES.N)
class EvaluationHelper(input: String) {
    private var cleanInput: MutableList<String>

    init {
        // convert x to *
        var newInput = StringBuilder()
        for (c in input) {
           if (c == '×') newInput.append('*')
            else newInput.append(c)
        }

        // take raw input, split into digits and operators
        val digits = newInput.split(Regex("(?<=[+\\-*/()^])|(?=[+\\-*/()^])"))

        // make the collections mutable and clean them of empty elements
        cleanInput = digits.toMutableList()
        cleanInput.removeIf { obj: String? -> obj == "" }
    }

    fun getAnswer(): String {
        val postfixStack = Stack()
        val opStack = Stack()
        val numStack = Stack()

        //from infix to postfix
        for (e in cleanInput) {

            // if we have '(' push onto opStack
            if (e == "("){
                opStack.push(e)
                continue
            }

            // if we have ')' pop until '(' in opstack is reached
            if (e == ")") {
                while (opStack.peek() != "(") {
                    postfixStack.push(opStack.pop())
                }
                // lastly, pop the (
                opStack.pop()
                continue
            }

            // if we have a digit, push it onto postfix
            if (priority(e) == -1) {
                postfixStack.push(e)
                continue
            }

            // if operator stack is empty (or e is '(' ) push the operator onto opstack
            if (opStack.isEmpty()) {
                opStack.push(e)
                continue
            }
            // if the operator stack is not empty do one of two things
            else {

                //... if priority is greater/equal to top of opstack push to opstack
                if (priority(e) > priority(opStack.peek() as String)) {
                    opStack.push(e)
                }
                //... if priority is less than the top of opstack
                else {

                    // push from opstack to postfix stack as long as top of opStack has greater or equal priority
                    // stop loop if opstack becomes empty, then push operator
                    while (priority(e) <= priority(opStack.peek() as String)) {
                        postfixStack.push(opStack.pop())
                        if (opStack.isEmpty()) break
                    }
                    opStack.push(e)
                }
            }
        }

        // pop out any leftover items in opStack
        while (!opStack.isEmpty()) {
            postfixStack.push(opStack.pop())
        }

        // now convert from postfix to answer
        for (e in postfixStack) {

            if (priority(e) == -1) numStack.push(e)
            else {
                var result = operate(numStack.pop(), numStack.pop(), e)
                numStack.push(result)
            }
        }

        return numStack.pop().toString()
    }

    private fun priority(e: Any): Int {
        return when (e) {
            "+", "-" -> 1
            "*", "/" -> 2
            "^" -> 3
            else -> -1
        }
    }

    private fun operate(op1: Any, op2: Any, operand: Any): Any {
        var x = op1.toString().toDouble()
        var y = op2.toString().toDouble()

        return when (operand) {
            "+" -> y + x
            "-" -> y - x
            "/" -> y / x
            "*" -> y * x
            "^" -> y.pow(x)
            else -> 0.0
        }
    }
}