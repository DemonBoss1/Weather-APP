package com.empire_mammoth.weatherapp.domain.usecase

import com.empire_mammoth.weatherapp.domain.model.City
import com.empire_mammoth.weatherapp.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CityUseCases @Inject constructor(
    private val repository: CityRepository
) {
    fun getCities(): Flow<List<City>> = repository.cities
    suspend fun setCurrent(cityId: String) = repository.setCurrentCity(cityId)
}