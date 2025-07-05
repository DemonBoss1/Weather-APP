package com.empire_mammoth.weatherapp.data.repository

import android.content.Context
import com.empire_mammoth.weatherapp.domain.repository.DefaultCityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultCityManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DefaultCityManager {
    private val prefs = context.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)

    override fun setDefaultCity(cityName: String) {
        prefs.edit().putString("default_city", cityName).apply()
    }

    override fun getDefaultCity(): String {
        return prefs.getString("default_city", "Moscow").toString()
    }
}