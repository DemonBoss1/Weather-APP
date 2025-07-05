package com.empire_mammoth.weatherapp.domain.repository

interface DefaultCityManager {
    fun setDefaultCity(cityName: String)
    fun getDefaultCity(): String
}
