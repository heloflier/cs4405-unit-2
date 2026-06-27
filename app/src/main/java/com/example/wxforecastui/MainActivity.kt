package com.example.wxforecastui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private var useFahrenheit = false

    private val weatherReport = HourlyWeatherReport(
        city = "Turin, Italy",
        temperatureCelsius = 22.0,
        condition = "Sunny",
        forecastTime = Calendar.getInstance().apply {
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.time
    )

    private lateinit var tvCity: TextView
    private lateinit var tvTemperature: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvLastUpdated: TextView
    private lateinit var btnToggleUnit: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvCity = findViewById(R.id.tvCity)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvCondition = findViewById(R.id.tvCondition)
        tvLastUpdated = findViewById(R.id.tvLastUpdated)
        btnToggleUnit = findViewById(R.id.btnToggleUnit)

        updateUI()

        btnToggleUnit.setOnClickListener {
            useFahrenheit = !useFahrenheit
            updateUI()
        }
    }

    private fun updateUI() {
        val formatter = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        tvCity.text = weatherReport.city
        tvTemperature.text = weatherReport.formatTemperatureDisplay(useFahrenheit)
        tvCondition.text = weatherReport.condition
        tvLastUpdated.text = "Last updated: ${formatter.format(weatherReport.forecastTime)}"
        btnToggleUnit.text = if (useFahrenheit) "Switch to °C" else "Switch to °F"
    }
}