package com.empire_mammoth.weatherapp.domain.model

data class City(
    val id: String,
    val name: String,
    val country: String,
    var isCurrent: Boolean = false
)