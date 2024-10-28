package com.example.currencyconverter

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var amountInput: EditText
    private lateinit var currencyFrom: Spinner
    private lateinit var currencyTo: Spinner
    private lateinit var convertButton: Button
    private lateinit var resultText: TextView

    private val currencies = listOf("USD", "EUR", "GBP", "INR")

    private val conversionRates = mapOf(
        "USD" to mapOf("EUR" to 0.85, "GBP" to 0.75, "INR" to 74.0),
        "EUR" to mapOf("USD" to 1.18, "GBP" to 0.88, "INR" to 87.0),
        "GBP" to mapOf("USD" to 1.33, "EUR" to 1.14, "INR" to 99.0),
        "INR" to mapOf("USD" to 0.013, "EUR" to 0.011, "GBP" to 0.010)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        amountInput = findViewById(R.id.amountInput)
        currencyFrom = findViewById(R.id.currencyFrom)
        currencyTo = findViewById(R.id.currencyTo)
        convertButton = findViewById(R.id.convertButton)
        resultText = findViewById(R.id.resultText)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currencyFrom.adapter = adapter
        currencyTo.adapter = adapter

        convertButton.setOnClickListener {
            val amount = amountInput.text.toString().toDoubleOrNull()
            if (amount != null) {
                val from = currencyFrom.selectedItem.toString()
                val to = currencyTo.selectedItem.toString()
                convertCurrency(amount, from, to)
            } else {
                resultText.text = "Please enter a valid amount."
            }
        }
    }

    private fun convertCurrency(amount: Double, from: String, to: String) {
        if (from == to) {
            resultText.text = "$amount $from = $amount $to"
            return
        }

        val rate = conversionRates[from]?.get(to) ?: 0.0
        if (rate == 0.0) {
            resultText.text = "Conversion rate not available."
            return
        }

        val convertedAmount = amount * rate
        resultText.text = "$amount $from = $convertedAmount $to"
    }
}
