package com.example.wxforecastui

import java.text.SimpleDateFormat
import java.util.Date

class HourlyWeatherReport(
    city: String,
    temperatureCelsius: Double,
    condition: String,
    lastUpdated: Date = Date(),
    val forecastTime: Date
) : WeatherReport(city, temperatureCelsius, condition, lastUpdated) {

    override fun toString(): String {
        val formatter = SimpleDateFormat("HH:mm")
        return "${formatter.format(forecastTime)} - ${formatTemperatureDisplay()} - $condition"
    }
}