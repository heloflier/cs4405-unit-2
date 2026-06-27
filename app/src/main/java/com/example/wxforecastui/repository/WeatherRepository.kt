package com.example.wxforecastui.repository

import com.example.wxforecastui.api.OpenMeteoService
import com.example.wxforecastui.api.WeatherResponse
import com.example.wxforecastui.mock.MockWeatherDataSource

class WeatherRepository(private val service: OpenMeteoService) {

    suspend fun getWeather(latitude: Double, longitude: Double): WeatherResponse {
        return try {
            service.getCurrentWeather(
                latitude = latitude,
                longitude = longitude,
                current = "temperature_2m,weathercode",
                timezone = "auto"
            )
        } catch (e: Exception) {
            MockWeatherDataSource.getWeather()
        }
    }
}