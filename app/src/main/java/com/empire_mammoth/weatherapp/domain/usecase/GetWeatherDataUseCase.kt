package com.empire_mammoth.weatherapp.domain.usecase

import com.empire_mammoth.weatherapp.domain.model.WeatherDataUI
import com.empire_mammoth.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(location: String): Result<WeatherDataUI> {
        return weatherRepository.getWeatherData(location)
    }
}