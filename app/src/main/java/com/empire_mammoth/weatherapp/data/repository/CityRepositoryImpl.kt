package com.empire_mammoth.weatherapp.data.repository

import com.empire_mammoth.weatherapp.domain.model.City
import com.empire_mammoth.weatherapp.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor() : CityRepository {

    private val _cities = mutableListOf(
        // Российские города (15)
        City("1", "Moscow", "Russia", true),
        City("2", "Saint Petersburg", "Russia"),
        City("3", "Novosibirsk", "Russia"),
        City("4", "Yekaterinburg", "Russia"),
        City("5", "Kazan", "Russia"),
        City("6", "Nizhny Novgorod", "Russia"),
        City("7", "Chelyabinsk", "Russia"),
        City("8", "Samara", "Russia"),
        City("9", "Omsk", "Russia"),
        City("10", "Rostov-on-Don", "Russia"),
        City("11", "Ufa", "Russia"),
        City("12", "Krasnoyarsk", "Russia"),
        City("13", "Perm", "Russia"),
        City("14", "Voronezh", "Russia"),
        City("15", "Volgograd", "Russia"),

        // Международные города (35)
        City("16", "London", "UK"),
        City("17", "New York", "USA"),
        City("18", "Paris", "France"),
        City("19", "Tokyo", "Japan"),
        City("20", "Berlin", "Germany"),
        City("21", "Rome", "Italy"),
        City("22", "Sydney", "Australia"),
        City("23", "Beijing", "China"),
        City("24", "Delhi", "India"),
        City("25", "Cairo", "Egypt"),
        City("26", "Rio de Janeiro", "Brazil"),
        City("27", "Toronto", "Canada"),
        City("28", "Dubai", "UAE"),
        City("29", "Seoul", "South Korea"),
        City("30", "Mexico City", "Mexico"),
        City("31", "Bangkok", "Thailand"),
        City("32", "Singapore", "Singapore"),
        City("33", "Istanbul", "Turkey"),
        City("34", "Amsterdam", "Netherlands"),
        City("35", "Barcelona", "Spain"),
        City("36", "Vienna", "Austria"),
        City("37", "Prague", "Czech Republic"),
        City("38", "Stockholm", "Sweden"),
        City("39", "Oslo", "Norway"),
        City("40", "Helsinki", "Finland"),
        City("41", "Copenhagen", "Denmark"),
        City("42", "Zurich", "Switzerland"),
        City("43", "Brussels", "Belgium"),
        City("44", "Dublin", "Ireland"),
        City("45", "Lisbon", "Portugal"),
        City("46", "Athens", "Greece"),
        City("47", "Warsaw", "Poland"),
        City("48", "Budapest", "Hungary"),
        City("49", "Cape Town", "South Africa"),
        City("50", "Anchorage", "USA")
    )

    private val _citiesFlow = MutableStateFlow(_cities.toList())
    override val cities: Flow<List<City>> = _citiesFlow

    override suspend fun setCurrentCity(cityId: String) {
        _cities.forEach { it.isCurrent = (it.id == cityId) }
        _citiesFlow.value = _cities.toList()
    }
}