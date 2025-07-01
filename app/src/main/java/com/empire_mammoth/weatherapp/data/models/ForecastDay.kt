package com.empire_mammoth.weatherapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Прогноз на день
 */
data class ForecastDay(
    // Дата (YYYY-MM-DD)
    @SerializedName("date") val date: String,

    // Дата (timestamp)
    @SerializedName("date_epoch") val dateEpoch: Long,

    // Дневной прогноз
    @SerializedName("day") val day: DayForecast,

    // Астрономические данные
    @SerializedName("astro") val astro: Astronomy,

    // Почасовой прогноз
    @SerializedName("hour") val hours: List<HourForecast>
)
