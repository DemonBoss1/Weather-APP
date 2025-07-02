package com.empire_mammoth.weatherapp.domain.model

import com.empire_mammoth.weatherapp.data.models.WeatherResponse

class WeatherState {
    sealed class WeatherState {
        object Loading : WeatherState()
        data class Success(val data: WeatherDataUI) : WeatherState()
        data class Error(val message: String) : WeatherState()
    }
}
