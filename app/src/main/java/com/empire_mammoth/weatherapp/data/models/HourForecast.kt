package com.empire_mammoth.weatherapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Почасовой прогноз
 */
data class HourForecast(
    // Время (timestamp)
    @SerializedName("time_epoch") val timeEpoch: Long,

    // Время
    @SerializedName("time") val time: String,

    // Температура (°C)
    @SerializedName("temp_c") val tempC: Double,

    // Температура (°F)
    @SerializedName("temp_f") val tempF: Double,

    // День (1) или ночь (0)
    @SerializedName("is_day") val isDay: Int,

    // Погодные условия
    @SerializedName("condition") val condition: WeatherCondition,

    // Скорость ветра (мили/ч)
    @SerializedName("wind_mph") val windMph: Double,

    // Скорость ветра (км/ч)
    @SerializedName("wind_kph") val windKph: Double,

    // Направление ветра (градусы)
    @SerializedName("wind_degree") val windDegree: Int,

    // Направление ветра (румбы)
    @SerializedName("wind_dir") val windDir: String,

    // Давление (мбар)
    @SerializedName("pressure_mb") val pressureMb: Double,

    // Давление (дюймы)
    @SerializedName("pressure_in") val pressureIn: Double,

    // Осадки (мм)
    @SerializedName("precip_mm") val precipMm: Double,

    // Осадки (дюймы)
    @SerializedName("precip_in") val precipIn: Double,

    // Влажность %
    @SerializedName("humidity") val humidity: Int,

    // Облачность %
    @SerializedName("cloud") val cloud: Int,

    // Ощущается как (°C)
    @SerializedName("feelslike_c") val feelslikeC: Double,

    // Ощущается как (°F)
    @SerializedName("feelslike_f") val feelslikeF: Double,

    // Охлаждение ветром (°C)
    @SerializedName("windchill_c") val windchillC: Double,

    // Охлаждение ветром (°F)
    @SerializedName("windchill_f") val windchillF: Double,

    // Тепловой индекс (°C)
    @SerializedName("heatindex_c") val heatindexC: Double,

    // Тепловой индекс (°F)
    @SerializedName("heatindex_f") val heatindexF: Double,

    // Точка росы (°C)
    @SerializedName("dewpoint_c") val dewpointC: Double,

    // Точка росы (°F)
    @SerializedName("dewpoint_f") val dewpointF: Double,

    // Будет ли дождь (1/0)
    @SerializedName("will_it_rain") val willItRain: Int,

    // Вероятность дождя %
    @SerializedName("chance_of_rain") val chanceOfRain: Int,

    // Будет ли снег (1/0)
    @SerializedName("will_it_snow") val willItSnow: Int,

    // Вероятность снега %
    @SerializedName("chance_of_snow") val chanceOfSnow: Int,

    // Видимость (км)
    @SerializedName("vis_km") val visKm: Double,

    // Видимость (мили)
    @SerializedName("vis_miles") val visMiles: Double,

    // Порывы ветра (мили/ч)
    @SerializedName("gust_mph") val gustMph: Double,

    // Порывы ветра (км/ч)
    @SerializedName("gust_kph") val gustKph: Double,

    // УФ-индекс
    @SerializedName("uv") val uv: Double
)
