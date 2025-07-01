package com.empire_mammoth.weatherapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Информация о местоположении
 */
data class Location(
    // Название города
    @SerializedName("name") val name: String,

    // Регион/область
    @SerializedName("region") val region: String,

    // Страна
    @SerializedName("country") val country: String,

    // Широта
    @SerializedName("lat") val lat: Double,

    // Долгота
    @SerializedName("lon") val lon: Double,

    // Часовой пояс
    @SerializedName("tz_id") val timezone: String,

    // Локальное время (timestamp)
    @SerializedName("localtime_epoch") val localtimeEpoch: Long,

    // Локальное время
    @SerializedName("localtime") val localtime: String
)
