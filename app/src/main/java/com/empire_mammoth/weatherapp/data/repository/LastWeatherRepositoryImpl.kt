package com.empire_mammoth.weatherapp.data.repository

import com.empire_mammoth.weatherapp.data.room.LastWeatherDao
import com.empire_mammoth.weatherapp.data.room.LastWeatherEntity
import com.empire_mammoth.weatherapp.domain.model.WeatherDataUI
import com.empire_mammoth.weatherapp.domain.repository.LastWeatherRepository
import com.google.gson.Gson
import javax.inject.Inject

class LastWeatherRepositoryImpl @Inject constructor(
    private val dao: LastWeatherDao,
    private val gson: Gson
)  : LastWeatherRepository {
    override suspend fun saveLastWeather(cityName: String, weather: WeatherDataUI) {
        dao.save(
            LastWeatherEntity(
                cityName = cityName,
                weatherJson = gson.toJson(weather)
            )
        )
    }

    override suspend fun getLastWeather(): Pair<String, WeatherDataUI>? {
        return dao.get()?.let { entity ->
            Pair(
                entity.cityName,
                gson.fromJson(entity.weatherJson, WeatherDataUI::class.java)
            )
        }
    }

    override suspend fun clearLastWeather() {
        dao.clear()
    }
}