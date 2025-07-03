package com.empire_mammoth.weatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.empire_mammoth.weatherapp.domain.model.CityListState
import com.empire_mammoth.weatherapp.domain.usecase.CityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityListViewModel @Inject constructor(
    private val cityUseCases: CityUseCases
) : ViewModel() {

    private val _state = MutableStateFlow<CityListState>(CityListState.Loading)
    val state: StateFlow<CityListState> = _state

    init {
        loadCities()
    }

    private fun loadCities() {
        viewModelScope.launch {
            cityUseCases.getCities()
                .collect { cities ->
                    _state.value = CityListState.Success(cities)
                }
        }
    }

    fun selectCity(cityId: String) {
        viewModelScope.launch {
            cityUseCases.setCurrent(cityId)
        }
    }
}