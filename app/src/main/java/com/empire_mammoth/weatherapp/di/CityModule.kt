package com.empire_mammoth.weatherapp.di

import com.empire_mammoth.weatherapp.data.repository.CityRepositoryImpl
import com.empire_mammoth.weatherapp.domain.repository.CityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CityModule {

    @Binds
    @Singleton
    abstract fun bindCityRepository(
        repository: CityRepositoryImpl
    ): CityRepository
}