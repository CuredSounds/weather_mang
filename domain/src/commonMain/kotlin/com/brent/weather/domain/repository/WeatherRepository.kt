package com.brent.weather.domain.repository

import com.brent.weather.domain.model.WeatherData

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, lon: Double): Result<WeatherData>
}
