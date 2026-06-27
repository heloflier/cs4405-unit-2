package com.example.wxforecastui.mock

import com.example.wxforecastui.api.CurrentWeather
import com.example.wxforecastui.api.WeatherResponse
import java.util.Calendar
import java.util.Date

object MockWeatherDataSource {

    val city = "Turin, Italy"

    val forecastTime: Date = Calendar.getInstance().apply {
        set(Calendar.MINUTE, 0)
    }.time

    fun getWeather(): WeatherResponse {
        return WeatherResponse(
            current = CurrentWeather(
                temperature = 22.0,
                weathercode = 0
            )
        )
    }
}