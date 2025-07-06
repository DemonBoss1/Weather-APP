package com.empire_mammoth.weatherapp.data.mapper

import com.empire_mammoth.weatherapp.data.models.Astronomy
import com.empire_mammoth.weatherapp.data.models.CurrentWeather
import com.empire_mammoth.weatherapp.data.models.ForecastDay
import com.empire_mammoth.weatherapp.data.models.HourForecast
import com.empire_mammoth.weatherapp.data.models.Location
import com.empire_mammoth.weatherapp.data.models.WeatherResponse
import com.empire_mammoth.weatherapp.domain.model.AstronomyUI
import com.empire_mammoth.weatherapp.domain.model.CurrentWeatherUI
import com.empire_mammoth.weatherapp.domain.model.DailyForecastUI
import com.empire_mammoth.weatherapp.domain.model.HourlyForecastUI
import com.empire_mammoth.weatherapp.domain.model.WeatherDataUI
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class WeatherMapper @Inject constructor() {

    fun mapToUI(response: WeatherResponse): WeatherDataUI {
        val hourlyForecast = prepare24HourForecast(
            currentDayHours = response.forecast.forecastDays.first().hours,
            nextDayHours = response.forecast.forecastDays.getOrNull(1)?.hours ?: emptyList(),
            location = response.location
        )

        return WeatherDataUI(
            current = mapCurrentWeather(response.location, response.current),
            dailyForecast = response.forecast.forecastDays.map { mapDailyForecast(it) },
            hourlyForecast = hourlyForecast,
            astronomy = mapAstronomy(response.forecast.forecastDays.first().astro)
        )
    }

    private fun getCurrentHourFromResponse(location: Location): Int {
        return location.localtime.substring(11, 13).toInt()
    }

    private fun prepare24HourForecast(
        currentDayHours: List<HourForecast>,
        nextDayHours: List<HourForecast>,
        location: Location
    ): List<HourlyForecastUI> {
        val timeZone = TimeZone.getTimeZone(location.timezone)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).apply {
            this.timeZone = timeZone
        }

        // Объединяем и маппим
        return (currentDayHours + nextDayHours).map { hour ->
            mapHourlyForecast(hour, location.timezone)
        }
    }

    private fun mapHourlyForecast(hour: HourForecast, timezone: String): HourlyForecastUI {
        return try {

            HourlyForecastUI(
                time = hour.time.substring(11),
                temp = hour.tempC,
                condition = hour.condition.text,
                iconUrl = "https:${hour.condition.icon}"
            )
        } catch (e: Exception) {
            defaultHourMapping(hour)
        }
    }

    private fun defaultHourMapping(hour: HourForecast): HourlyForecastUI {
        return HourlyForecastUI(
            time = hour.time.substring(11, 16),
            temp = hour.tempC,
            condition = hour.condition.text,
            iconUrl = "https:${hour.condition.icon}"
        )
    }

    // Остальные методы остаются без изменений
    private fun mapCurrentWeather(location: Location, current: CurrentWeather): CurrentWeatherUI {
        return CurrentWeatherUI(
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
        )
    }

    private fun mapDailyForecast(forecastDay: ForecastDay): DailyForecastUI {
        return DailyForecastUI(
            date = forecastDay.date,
            maxTemp = forecastDay.day.maxTempC,
            minTemp = forecastDay.day.minTempC,
            condition = forecastDay.day.condition.text,
            iconUrl = "https:${forecastDay.day.condition.icon}",
            chanceOfRain = forecastDay.day.dailyChanceOfRain
        )
    }

    private fun mapAstronomy(astronomy: Astronomy): AstronomyUI {
        return AstronomyUI(
            sunrise = astronomy.sunrise,
            sunset = astronomy.sunset,
            moonrise = astronomy.moonrise,
            moonPhase = astronomy.moonPhase
        )
    }
}