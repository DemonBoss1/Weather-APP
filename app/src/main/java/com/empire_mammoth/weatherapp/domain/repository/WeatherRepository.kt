package com.empire_mammoth.weatherapp.domain.repository

import com.empire_mammoth.weatherapp.domain.model.WeatherDataUI

interface WeatherRepository {
    suspend fun getWeatherData(location: String): Result<WeatherDataUI>
}