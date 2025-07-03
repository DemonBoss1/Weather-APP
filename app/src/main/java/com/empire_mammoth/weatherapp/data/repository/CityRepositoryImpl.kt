package com.empire_mammoth.weatherapp.data.repository

import com.empire_mammoth.weatherapp.domain.model.City
import com.empire_mammoth.weatherapp.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor() : CityRepository {

    private val _cities = mutableListOf(
        City("1", "Moscow", "Russia", true),
        City("2", "London", "UK"),
        City("3", "New York", "USA"),
        City("4", "Paris", "France"),
        City("5", "Tokyo", "Japan")
    )

    private val _citiesFlow = MutableStateFlow(_cities.toList())
    override val cities: Flow<List<City>> = _citiesFlow

    override suspend fun setCurrentCity(cityId: String) {
        _cities.forEach { it.isCurrent = (it.id == cityId) }
        _citiesFlow.value = _cities.toList()
    }
}