package com.empire_mammoth.weatherapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.empire_mammoth.weatherapp.BuildConfig
import com.empire_mammoth.weatherapp.data.api.WeatherApiService
import com.empire_mammoth.weatherapp.data.models.WeatherResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val weatherApi: WeatherApiService
) : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getWeather(location: String, days: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = weatherApi.getWeatherForecast(
                    apiKey = BuildConfig.WEATHER_API_KEY,
                    location = location,
                    days = days
                )
                _weatherData.value = response
            } catch (e: Exception) {
                _error.value = "Failed to load weather data: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
