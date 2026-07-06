package com.brent.weather.domain.model

data class WeatherData(
    val locationName: String,
    val timestamp: Long,
    val temperatureCelsius: Double,
    val feelsLikeCelsius: Double,
    val humidityPercentage: Int,
    val windSpeedKph: Double,
    val conditionDescription: String,
    val iconCode: String
)
