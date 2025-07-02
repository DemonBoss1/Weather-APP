package com.empire_mammoth.weatherapp.domain.model

data class HourlyForecastUI(
    val time: String,
    val temp: Double,
    val condition: String,
    val iconUrl: String
)