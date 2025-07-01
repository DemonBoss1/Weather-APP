package com.empire_mammoth.weatherapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Погодные условия
 */
data class WeatherCondition(
    // Текстовое описание
    @SerializedName("text") val text: String,

    // Иконка погоды
    @SerializedName("icon") val icon: String,

    // Код условия
    @SerializedName("code") val code: Int
)
