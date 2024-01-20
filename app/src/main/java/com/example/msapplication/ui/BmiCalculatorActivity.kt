package com.example.msapplication.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.msapplication.R

class BmiCalculatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_calculator)

        val weightEt = findViewById<EditText>(R.id.weight_et!!)
        val heightEt = findViewById<EditText>(R.id.height_et!!)
        val calculateBtn = findViewById<Button>(R.id.calculate_btn!!)

        calculateBtn.setOnClickListener {
            val weight = weightEt.text.toString()
            val height = heightEt.text.toString()

            if (validateInput(weight, height)) {
                val bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                val bmi2Digits = String.format("%.2f", bmi).toFloat()

                displayResult(bmi2Digits)
            }
        }
    }

    private fun validateInput(weight: String?, height: String?): Boolean {
        return if (weight.isNullOrEmpty()) {
            Toast.makeText(this, "Please enter your weight", Toast.LENGTH_SHORT).show()
            false
        } else if (height.isNullOrEmpty()) {
            Toast.makeText(this, "Please enter your height", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    private fun displayResult(bmi: Float) {

        val resultIndex = findViewById<TextView>(R.id.result_index_tv)
        val resultDescription = findViewById<TextView>(R.id.result_description_tv)
        val resultInfo = findViewById<TextView>(R.id.result_info_tv)

        resultIndex.text = bmi.toString()
        if (bmi < 18.5) {
            resultDescription.text = "Unhealthy"
            resultInfo.text = "(Underweight < 18.5)"
        } else if (bmi in 18.5..22.9) {
            resultDescription.text = "Healthy"
            resultInfo.text = "(Normal range is 18.5 – 22.9)"
        } else if (bmi in 23.0..24.9) {
            resultDescription.text = "At risk"
            resultInfo.text = "(OverweightI range is 23.0 - 24.9)"
        } else if (bmi in 25.0..29.9) {
            resultDescription.text = "Moderately obese"
            resultInfo.text = "(OverweightII range is 25.0 – 29.9)"
        } else if (bmi >= 30.0) {
            resultDescription.text = "Severely obese"
            resultInfo.text = "(OverweightIII ≥ 30.0)"
        }

    }

}