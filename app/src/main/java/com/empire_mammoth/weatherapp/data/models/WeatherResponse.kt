package com.empire_mammoth.weatherapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Основной ответ API погоды
 */
data class WeatherResponse(
    // Локация
    @SerializedName("location") val location: Location,

    // Текущая погода
    @SerializedName("current") val current: CurrentWeather,

    // Прогноз
    @SerializedName("forecast") val forecast: Forecast
)