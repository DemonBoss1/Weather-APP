package com.empire_mammoth.weatherapp.domain.model

sealed class CityListState {
    object Loading : CityListState()
    data class Success(val cities: List<City>) : CityListState()
}