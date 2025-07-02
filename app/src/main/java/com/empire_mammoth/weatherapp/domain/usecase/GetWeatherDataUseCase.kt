package com.empire_mammoth.weatherapp.domain.usecase

import com.empire_mammoth.weatherapp.BuildConfig
import com.empire_mammoth.weatherapp.data.api.WeatherApiService
import com.empire_mammoth.weatherapp.data.models.WeatherResponse
import com.empire_mammoth.weatherapp.domain.model.AstronomyUI
import com.empire_mammoth.weatherapp.domain.model.HourlyForecastUI
import com.empire_mammoth.weatherapp.domain.model.CurrentWeatherUI
import com.empire_mammoth.weatherapp.domain.model.DailyForecastUI
import com.empire_mammoth.weatherapp.domain.model.WeatherDataUI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(
    private val weatherApi: WeatherApiService,
) {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    suspend operator fun invoke(location: String): Result<WeatherDataUI> = withContext(dispatcher) {
        try {
            val response = weatherApi.getWeatherForecast(
                apiKey = BuildConfig.WEATHER_API_KEY,
                location = location,
                days = 3
            )
            Result.success(response.toUI())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun WeatherResponse.toUI(): WeatherDataUI {
        return WeatherDataUI(
            current = CurrentWeatherUI(
                location = location.name,
                region = "${location.region}, ${location.country}",
                temperature = current.tempC,
                feelsLike = current.feelslikeC,
                condition = current.condition.text,
                iconUrl = "https:${current.condition.icon}",
                humidity = current.humidity,
                windSpeed = current.windKph,
                windDirection = current.windDir,
                lastUpdated = current.lastUpdated
            ),
            dailyForecast = forecast.forecastDays.map { day ->
                DailyForecastUI(
                    date = day.date,
                    maxTemp = day.day.maxTempC,
                    minTemp = day.day.minTempC,
                    condition = day.day.condition.text,
                    iconUrl = "https:${day.day.condition.icon}",
                    chanceOfRain = day.day.dailyChanceOfRain
                )
            },
            hourlyForecast = forecast.forecastDays.first().hours.map { hour ->
                HourlyForecastUI(
                    time = hour.time.substring(11, 16),
                    temp = hour.tempC,
                    condition = hour.condition.text,
                    iconUrl = "https:${hour.condition.icon}"
                )
            },
            astronomy = AstronomyUI(
                sunrise = forecast.forecastDays.first().astro.sunrise,
                sunset = forecast.forecastDays.first().astro.sunset,
                moonrise = forecast.forecastDays.first().astro.moonrise,
                moonPhase = forecast.forecastDays.first().astro.moonPhase
            )
        )
    }
}