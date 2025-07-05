package com.empire_mammoth.weatherapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_weather")
data class LastWeatherEntity(
    @PrimaryKey val id: Int = 1,
    val cityName: String,
    val weatherJson: String
)