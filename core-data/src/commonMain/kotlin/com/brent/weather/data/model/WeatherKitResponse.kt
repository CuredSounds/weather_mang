package com.brent.weather.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherKitResponse(
    @SerialName("currentWeather") val currentWeather: WkCurrentWeather
)

@Serializable
data class WkCurrentWeather(
    @SerialName("asOf") val asOf: String,
    @SerialName("temperature") val temperature: Double,
    @SerialName("feelsLike") val feelsLike: Double,
    @SerialName("humidity") val humidity: Double, // WeatherKit humidity is typically 0.0 to 1.0
    @SerialName("windSpeed") val windSpeed: Double, // WeatherKit windSpeed is typically in km/h or m/s
    @SerialName("conditionCode") val conditionCode: String
)
