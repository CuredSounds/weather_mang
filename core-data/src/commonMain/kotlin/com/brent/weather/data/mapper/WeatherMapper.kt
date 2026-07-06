package com.brent.weather.data.mapper

import com.brent.weather.domain.model.WeatherData

interface WeatherMapper<T> {
    fun mapToDomain(apiResponse: T): WeatherData
}
