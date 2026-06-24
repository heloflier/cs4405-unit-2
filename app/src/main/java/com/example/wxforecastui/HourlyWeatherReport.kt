package com.example.wxforecastui

import java.util.Date

class HourlyWeatherReport(
    city: String,
    temperatureCelsius: Double,
    condition: String,
    lastUpdated: Date = Date(),
    val hour: Int
) : WeatherReport(city, temperatureCelsius, condition, lastUpdated) {

    override fun toString(): String {
        return "Hour $hour: ${formatTemperatureDisplay()} - $condition"
    }
}