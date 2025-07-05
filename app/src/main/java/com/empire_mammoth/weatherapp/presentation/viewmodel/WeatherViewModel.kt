package com.empire_mammoth.weatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.empire_mammoth.weatherapp.BuildConfig
import com.empire_mammoth.weatherapp.data.api.WeatherApiService
import com.empire_mammoth.weatherapp.domain.model.WeatherState.WeatherState
import com.empire_mammoth.weatherapp.domain.repository.DefaultCityManager
import com.empire_mammoth.weatherapp.domain.usecase.GetLastWeatherUseCase
import com.empire_mammoth.weatherapp.domain.usecase.GetWeatherDataUseCase
import com.empire_mammoth.weatherapp.domain.usecase.SaveLastWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherData: GetWeatherDataUseCase,
    private val getLastWeatherUseCase: GetLastWeatherUseCase,
    private val saveLastWeatherUseCase: SaveLastWeatherUseCase,
    private val defaultCityManager: DefaultCityManager,
) : ViewModel() {
    var currentCity: String? = null
        private set

    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    private val _isDefaultCity = MutableStateFlow<String?>(defaultCityManager.getDefaultCity())
    val defaultCity: StateFlow<String?> = _isDefaultCity

    init {
        viewModelScope.launch {
            getLastWeatherUseCase()?.let { (cachedCity, cachedWeather) ->
                _weatherState.value = WeatherState.Success(cachedWeather)
                currentCity = cachedCity
            }
        }
    }

    fun setDefaultCity() {
        currentCity?.let { cityName ->
            defaultCityManager.setDefaultCity(cityName)
            _isDefaultCity.value = cityName
            viewModelScope.launch {
                saveLastWeatherUseCase(cityName, (_weatherState.value as WeatherState.Success).data)
            }
        }
    }

    fun getWeather(location: String) {
        currentCity = location
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading
            getWeatherData(location)
                .onSuccess { data ->
                    _weatherState.value = WeatherState.Success(data)
                    if (location == defaultCity.value)
                        saveLastWeatherUseCase(location, data)
                }
                .onFailure { exception ->
                    _weatherState.value = WeatherState.Error(
                        message = "Ошибка загрузки: ${exception.message ?: "Неизвестная ошибка"}"
                    )
                }
        }
    }
}
