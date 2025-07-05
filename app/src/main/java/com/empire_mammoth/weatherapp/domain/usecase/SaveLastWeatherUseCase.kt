package com.empire_mammoth.weatherapp.domain.usecase

import com.empire_mammoth.weatherapp.domain.model.WeatherDataUI
import com.empire_mammoth.weatherapp.domain.repository.LastWeatherRepository
import javax.inject.Inject

class SaveLastWeatherUseCase @Inject constructor(
    private val repository: LastWeatherRepository
) {
    suspend operator fun invoke(cityName: String, weather: WeatherDataUI) {
        repository.saveLastWeather(cityName, weather)
    }
}