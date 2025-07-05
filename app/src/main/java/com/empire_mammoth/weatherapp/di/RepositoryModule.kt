package com.empire_mammoth.weatherapp.di

import com.empire_mammoth.weatherapp.data.repository.CityRepositoryImpl
import com.empire_mammoth.weatherapp.data.repository.DefaultCityManagerImpl
import com.empire_mammoth.weatherapp.data.repository.LastWeatherRepositoryImpl
import com.empire_mammoth.weatherapp.data.repository.WeatherRepositoryImpl
import com.empire_mammoth.weatherapp.domain.repository.CityRepository
import com.empire_mammoth.weatherapp.domain.repository.DefaultCityManager
import com.empire_mammoth.weatherapp.domain.repository.LastWeatherRepository
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

    @Binds
    @Singleton
    abstract fun bindLastWeatherRepository(
        repository: LastWeatherRepositoryImpl
    ): LastWeatherRepository

    @Binds
    @Singleton
    abstract fun bindDefaultCityManager(
        repository: DefaultCityManagerImpl
    ): DefaultCityManager
}