package com.empire_mammoth.weatherapp.domain.model

data class CurrentWeatherUI(
    val location: String,
    val region: String,
    val temperature: Double,
    val feelsLike: Double,
    val condition: String,
    val iconUrl: String,
    val humidity: Int,
    val windSpeed: Double,
    val windDirection: String,
    val lastUpdated: String
)

