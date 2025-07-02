package com.empire_mammoth.weatherapp.domain.model

data class WeatherDataUI(
    val current: CurrentWeatherUI,
    val dailyForecast: List<DailyForecastUI>,
    val hourlyForecast: List<HourlyForecastUI>,
    val astronomy: AstronomyUI
)