package com.empire_mammoth.weatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.empire_mammoth.weatherapp.domain.model.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _selectedCity = MutableStateFlow<City?>(null)
    val selectedCity: StateFlow<City?> = _selectedCity.asStateFlow()

    fun selectCity(city: City) {
        _selectedCity.value = city
    }
}