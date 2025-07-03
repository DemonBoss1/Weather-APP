package com.empire_mammoth.weatherapp.domain.repository

import com.empire_mammoth.weatherapp.domain.model.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    val cities: Flow<List<City>>
    suspend fun setCurrentCity(cityId: String)
}