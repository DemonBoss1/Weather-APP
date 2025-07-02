package com.empire_mammoth.weatherapp.domain.model

data class DailyForecastUI(
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val condition: String,
    val iconUrl: String,
    val chanceOfRain: Int
)