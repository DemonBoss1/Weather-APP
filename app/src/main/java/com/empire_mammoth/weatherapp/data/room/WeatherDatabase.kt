package com.empire_mammoth.weatherapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LastWeatherEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): LastWeatherDao
}