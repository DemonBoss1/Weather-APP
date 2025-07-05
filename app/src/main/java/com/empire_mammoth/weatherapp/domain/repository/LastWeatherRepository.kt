package com.empire_mammoth.weatherapp.domain.repository

import com.empire_mammoth.weatherapp.domain.model.WeatherDataUI

interface LastWeatherRepository {

    suspend fun saveLastWeather(cityName: String, weather: WeatherDataUI)
    suspend fun getLastWeather(): Pair<String, WeatherDataUI>?
    suspend fun clearLastWeather()
}
