package com.brent.weather.data.mapper

import com.brent.weather.data.model.OwmResponse
import com.brent.weather.domain.model.WeatherData

class OwmWeatherMapper : WeatherMapper<OwmResponse> {
    override fun mapToDomain(apiResponse: OwmResponse): WeatherData {
        return WeatherData(
            locationName = apiResponse.cityName,
            timestamp = apiResponse.timestamp,
            temperatureCelsius = apiResponse.main.temp,
            feelsLikeCelsius = apiResponse.main.feelsLike,
            humidityPercentage = apiResponse.main.humidity,
            windSpeedKph = apiResponse.wind.speed * 3.6, // Convert m/s to km/h
            conditionDescription = apiResponse.weather.firstOrNull()?.description ?: "Unknown",
            iconCode = apiResponse.weather.firstOrNull()?.icon ?: "01d"
        )
    }
}
