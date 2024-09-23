package com.cs407.calculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val s = "ERROR: Cannot divide by zero"

class MainActivity : AppCompatActivity() {
    private lateinit var firstNumberEditText: EditText
    private lateinit var secondNumberEditText: EditText
    private lateinit var addButton: Button
    private lateinit var subtractButton: Button
    private lateinit var multiplyButton: Button
    private lateinit var divideButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firstNumberEditText = findViewById<EditText>(R.id.firstNumberEditText)
        secondNumberEditText = findViewById<EditText>(R.id.secondNumberEditText)
        addButton = findViewById<Button>(R.id.addButton)
        subtractButton = findViewById<Button>(R.id.subtractButton)
        multiplyButton = findViewById<Button>(R.id.multiplyButton)
        divideButton = findViewById<Button>(R.id.divideButton)

        addButton.setOnClickListener { calculate('+') }
        subtractButton.setOnClickListener { calculate('-') }
        multiplyButton.setOnClickListener { calculate('*') }
        divideButton.setOnClickListener { calculate('/') }
    }

    private fun calculate(operator: Char) {
        val firstNumber = firstNumberEditText.text.toString().toIntOrNull()
        val secondNumber = secondNumberEditText.text.toString().toIntOrNull()

        if (firstNumber == null || secondNumber == null) {
            Toast.makeText(this, getString(R.string.err_enter_valid_numbers),
                Toast.LENGTH_SHORT).show()
            return
        }

        val result = when (operator) {
            '+' -> firstNumber + secondNumber
            '-' -> firstNumber - secondNumber
            '*' -> firstNumber * secondNumber
            '/' -> {
                if (secondNumber == 0) {
                    Toast.makeText(this, getString(R.string.err_divide_by_zero),
                        Toast.LENGTH_SHORT).show()
                    return
                }
                firstNumber.toFloat() / secondNumber
            }
            else -> throw IllegalArgumentException(getString(R.string.exc_invalid_operator))
        }

        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("CAL_RESULT", result.toString())
        startActivity(intent)
    }
}