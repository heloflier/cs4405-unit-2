package com.example.wxforecastui

import java.util.Date

class WeatherReport(
    val city: String,
    val temperatureCelsius: Double,
    val condition: String,
    val lastUpdated: Date = Date()
) {
    fun toFahrenheit(): Double {
        return (temperatureCelsius * 9 / 5) + 32
    }

    fun toCelsius(fahrenheit: Double): Double {
        return (fahrenheit - 32) * 5 / 9
    }
}