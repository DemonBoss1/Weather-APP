package com.empire_mammoth.weatherapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Прогноз погоды
 */
data class Forecast(
    // Дни прогноза
    @SerializedName("forecastday") val forecastDays: List<ForecastDay>
)
