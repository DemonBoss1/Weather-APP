package com.empire_mammoth.weatherapp.data.repository

import com.empire_mammoth.weatherapp.BuildConfig
import com.empire_mammoth.weatherapp.data.api.WeatherApiService
import com.empire_mammoth.weatherapp.data.mapper.WeatherMapper
import com.empire_mammoth.weatherapp.data.models.Astronomy
import com.empire_mammoth.weatherapp.data.models.ForecastDay
import com.empire_mammoth.weatherapp.data.models.HourForecast
import com.empire_mammoth.weatherapp.data.models.WeatherResponse
import com.empire_mammoth.weatherapp.domain.model.AstronomyUI
import com.empire_mammoth.weatherapp.domain.model.CurrentWeatherUI
import com.empire_mammoth.weatherapp.domain.model.DailyForecastUI
import com.empire_mammoth.weatherapp.domain.model.HourlyForecastUI
import com.empire_mammoth.weatherapp.domain.model.WeatherDataUI
import com.empire_mammoth.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApiService,
    private val weatherMapper: WeatherMapper,
) : WeatherRepository {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getWeatherData(location: String): Result<WeatherDataUI> =
        withContext(dispatcher) {
            try {
                val response = weatherApi.getWeatherForecast(
                    apiKey = BuildConfig.WEATHER_API_KEY,
                    location = location,
                    days = 3
                )
                Result.success(weatherMapper.mapToUI(response))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}