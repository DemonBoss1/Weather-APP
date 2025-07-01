package com.empire_mammoth.weatherapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Дневной прогноз
 */
data class DayForecast(
    // Макс. температура (°C)
    @SerializedName("maxtemp_c") val maxTempC: Double,

    // Макс. температура (°F)
    @SerializedName("maxtemp_f") val maxTempF: Double,

    // Мин. температура (°C)
    @SerializedName("mintemp_c") val minTempC: Double,

    // Мин. температура (°F)
    @SerializedName("mintemp_f") val minTempF: Double,

    // Средняя температура (°C)
    @SerializedName("avgtemp_c") val avgTempC: Double,

    // Средняя температура (°F)
    @SerializedName("avgtemp_f") val avgTempF: Double,

    // Макс. скорость ветра (мили/ч)
    @SerializedName("maxwind_mph") val maxWindMph: Double,

    // Макс. скорость ветра (км/ч)
    @SerializedName("maxwind_kph") val maxWindKph: Double,

    // Всего осадков (мм)
    @SerializedName("totalprecip_mm") val totalPrecipMm: Double,

    // Всего осадков (дюймы)
    @SerializedName("totalprecip_in") val totalPrecipIn: Double,

    // Снег (см)
    @SerializedName("totalsnow_cm") val totalSnowCm: Double,

    // Средняя видимость (км)
    @SerializedName("avgvis_km") val avgVisKm: Double,

    // Средняя видимость (мили)
    @SerializedName("avgvis_miles") val avgVisMiles: Double,

    // Средняя влажность %
    @SerializedName("avghumidity") val avgHumidity: Double,

    // Будет ли дождь (1/0)
    @SerializedName("daily_will_it_rain") val dailyWillItRain: Int,

    // Вероятность дождя %
    @SerializedName("daily_chance_of_rain") val dailyChanceOfRain: Int,

    // Будет ли снег (1/0)
    @SerializedName("daily_will_it_snow") val dailyWillItSnow: Int,

    // Вероятность снега %
    @SerializedName("daily_chance_of_snow") val dailyChanceOfSnow: Int,

    // Погодные условия
    @SerializedName("condition") val condition: WeatherCondition,

    // УФ-индекс
    @SerializedName("uv") val uv: Double
)
