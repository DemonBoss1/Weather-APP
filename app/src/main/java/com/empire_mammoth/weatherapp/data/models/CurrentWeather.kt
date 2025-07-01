package com.empire_mammoth.weatherapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Текущие погодные условия
 */
data class CurrentWeather(
    // Время последнего обновления (timestamp)
    @SerializedName("last_updated_epoch") val lastUpdatedEpoch: Long,

    // Время последнего обновления
    @SerializedName("last_updated") val lastUpdated: String,

    // Температура в °C
    @SerializedName("temp_c") val tempC: Double,

    // Температура в °F
    @SerializedName("temp_f") val tempF: Double,

    // День (1) или ночь (0)
    @SerializedName("is_day") val isDay: Int,

    // Погодные условия
    @SerializedName("condition") val condition: WeatherCondition,

    // Скорость ветра в милях/ч
    @SerializedName("wind_mph") val windMph: Double,

    // Скорость ветра в км/ч
    @SerializedName("wind_kph") val windKph: Double,

    // Направление ветра (градусы)
    @SerializedName("wind_degree") val windDegree: Int,

    // Направление ветра (румбы)
    @SerializedName("wind_dir") val windDir: String,

    // Давление в миллибарах
    @SerializedName("pressure_mb") val pressureMb: Double,

    // Давление в дюймах рт.ст.
    @SerializedName("pressure_in") val pressureIn: Double,

    // Осадки в мм
    @SerializedName("precip_mm") val precipMm: Double,

    // Осадки в дюймах
    @SerializedName("precip_in") val precipIn: Double,

    // Влажность %
    @SerializedName("humidity") val humidity: Int,

    // Облачность %
    @SerializedName("cloud") val cloud: Int,

    // Ощущается как (°C)
    @SerializedName("feelslike_c") val feelslikeC: Double,

    // Ощущается как (°F)
    @SerializedName("feelslike_f") val feelslikeF: Double,

    // Видимость в км
    @SerializedName("vis_km") val visKm: Double,

    // Видимость в милях
    @SerializedName("vis_miles") val visMiles: Double,

    // УФ-индекс
    @SerializedName("uv") val uv: Double,

    // Порывы ветра (мили/ч)
    @SerializedName("gust_mph") val gustMph: Double,

    // Порывы ветра (км/ч)
    @SerializedName("gust_kph") val gustKph: Double
)
