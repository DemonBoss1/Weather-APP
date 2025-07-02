package com.empire_mammoth.weatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.empire_mammoth.weatherapp.BuildConfig
import com.empire_mammoth.weatherapp.data.api.WeatherApiService
import com.empire_mammoth.weatherapp.domain.model.WeatherState.WeatherState
import com.empire_mammoth.weatherapp.domain.usecase.GetWeatherDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherData: GetWeatherDataUseCase
) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    fun getWeather(location: String) {
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading
            getWeatherData(location)
                .onSuccess { data ->
                    _weatherState.value = WeatherState.Success(data)
                }
                .onFailure { exception ->
                    _weatherState.value = WeatherState.Error(
                        message = "Ошибка загрузки: ${exception.message ?: "Неизвестная ошибка"}"
                    )
                }
        }
    }
}
