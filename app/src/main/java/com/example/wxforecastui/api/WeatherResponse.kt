package com.example.wxforecastui.api

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("temperature_2m") val temperature: Double,
    @SerializedName("weathercode") val weathercode: Int
)

data class WeatherResponse(
    @SerializedName("current") val current: CurrentWeather
)