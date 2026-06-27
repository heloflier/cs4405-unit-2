package com.example.wxforecastui.api

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoService {

    @GET("v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "temperature_2m,weathercode",
        @Query("timezone") timezone: String = "auto"
    ): WeatherResponse
}