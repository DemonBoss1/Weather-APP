package com.empire_mammoth.weatherapp.di

import com.empire_mammoth.weatherapp.data.repository.CityRepositoryImpl
import com.empire_mammoth.weatherapp.data.repository.WeatherRepositoryImpl
import com.empire_mammoth.weatherapp.domain.repository.CityRepository
import com.empire_mammoth.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCityRepository(
        repository: CityRepositoryImpl
    ): CityRepository

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        repository: WeatherRepositoryImpl
    ): WeatherRepository
}