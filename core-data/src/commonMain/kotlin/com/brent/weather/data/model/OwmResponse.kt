package com.brent.weather.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwmResponse(
    @SerialName("name") val cityName: String,
    @SerialName("dt") val timestamp: Long,
    @SerialName("main") val main: OwmMain,
    @SerialName("wind") val wind: OwmWind,
    @SerialName("weather") val weather: List<OwmWeather>
)

@Serializable
data class OwmMain(
    @SerialName("temp") val temp: Double,
    @SerialName("feels_like") val feelsLike: Double,
    @SerialName("humidity") val humidity: Int
)

@Serializable
data class OwmWind(
    @SerialName("speed") val speed: Double
)

@Serializable
data class OwmWeather(
    @SerialName("description") val description: String,
    @SerialName("icon") val icon: String
)
