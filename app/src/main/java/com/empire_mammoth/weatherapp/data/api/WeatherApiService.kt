package com.empire_mammoth.weatherapp.data.api

import com.empire_mammoth.weatherapp.data.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("v1/forecast.json")
    suspend fun getWeatherForecast(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int
    ): WeatherResponse
}