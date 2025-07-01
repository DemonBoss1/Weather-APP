package com.empire_mammoth.weatherapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Астрономические данные
 */
data class Astronomy(
    // Восход солнца
    @SerializedName("sunrise") val sunrise: String,

    // Закат солнца
    @SerializedName("sunset") val sunset: String,

    // Восход луны
    @SerializedName("moonrise") val moonrise: String,

    // Закат луны
    @SerializedName("moonset") val moonset: String,

    // Фаза луны
    @SerializedName("moon_phase") val moonPhase: String,

    // Освещенность луны %
    @SerializedName("moon_illumination") val moonIllumination: Int,

    // Видна ли луна (1/0)
    @SerializedName("is_moon_up") val isMoonUp: Int,

    // Видно ли солнце (1/0)
    @SerializedName("is_sun_up") val isSunUp: Int
)
