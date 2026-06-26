package com.example.wxforecastui

import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherReportTest {

    private val report = WeatherReport(
        city = "Turin, Italy",
        temperatureCelsius = 0.0,
        condition = "Sunny"
    )

    @Test
    fun celsiusToFahrenheit_freezingPoint() {
        assertEquals(32.0, report.toFahrenheit(), 0.01)
    }

    @Test
    fun celsiusToFahrenheit_boilingPoint() {
        val boiling = WeatherReport("", 100.0, "")
        assertEquals(212.0, boiling.toFahrenheit(), 0.01)
    }

    @Test
    fun fahrenheitToCelsius_freezingPoint() {
        assertEquals(0.0, report.toCelsius(32.0), 0.01)
    }

    @Test
    fun fahrenheitToCelsius_boilingPoint() {
        assertEquals(100.0, report.toCelsius(212.0), 0.01)
    }
}