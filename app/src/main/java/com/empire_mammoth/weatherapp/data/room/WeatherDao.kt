package com.empire_mammoth.weatherapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LastWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(lastWeather: LastWeatherEntity)

    @Query("SELECT * FROM last_weather WHERE id = 1")
    suspend fun get(): LastWeatherEntity?

    @Query("DELETE FROM last_weather WHERE id = 1")
    suspend fun clear()
}