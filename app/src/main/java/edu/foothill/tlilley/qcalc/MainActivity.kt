package edu.foothill.tlilley.qcalc

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calcButtons = listOf<View>(btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven,
        btnEight, btnNine, btnZero, btnDec, btnClear, btnDelete, btnSqrt, btnPow, btnPar, btnPlus,
        btnMinus, btnMult, btnDiv, btnEnter)

        calcButtons.forEach{ it.setOnClickListener(this) }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View) {
        when(v.id) {

            // entered character will be exactly the same as the button char
            R.id.btnSqrt, R.id.btnPow, R.id.btnPlus, R.id.btnMult, R.id.btnOne, R.id.btnTwo,
            R.id.btnThree, R.id.btnFour, R.id.btnFive, R.id.btnSix, R.id.btnSeven, R.id.btnEight,
            R.id.btnNine, R.id.btnZero -> updateCalc(findViewById<Button>(v.id).text.toString())

            // entered character will be different from the button char
            R.id.btnDec -> updateCalc(".")
            R.id.btnDiv -> updateCalc("/")
            R.id.btnMinus -> updateCalc("-")

            // detect whether an opening or closing parentheses will be added
            R.id.btnPar -> {
                if (hasOpenPar()) updateCalc(")")
                else updateCalc("(")
            }

            // clear tvCalc text
            R.id.btnClear -> {
                tvCalc.text = ""
                tvAns.text = ""
            }

            // remove one char from tvCalc
            R.id.btnDelete -> {
                var text = tvCalc.text.toString().dropLast(1)
                tvCalc.text = text
            }

            // clear tvCalc and move tvCalc text to tvStorage text
            // calculate the answer
            R.id.btnEnter -> {
                val eh = EvaluationHelper(tvCalc.text as String)
                tvStored.text = "${tvCalc.text}"
                tvAns.text = eh.getAnswer()
                tvStoredAnswer.text = "= ${eh.getAnswer()}"
            }
        }
    }

    // update the tvCalc text
    @SuppressLint("SetTextI18n")
    private fun updateCalc(char: String) {
        tvCalc.text = "${tvCalc.text}$char"
    }

    // if there is an even number of '(' and ')', add a '('
    // if there is an odd number of '(' and ')', add a ')'
    private fun hasOpenPar(): Boolean {
        val text = tvCalc.text.toString()
        var openCounter = 0

        for (char in text) {
            if (char == '(' || char == ')') openCounter++
        }

        return openCounter % 2 != 0
    }


}