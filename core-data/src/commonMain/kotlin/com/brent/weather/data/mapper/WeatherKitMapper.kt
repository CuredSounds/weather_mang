package com.brent.weather.data.mapper

import com.brent.weather.data.model.WeatherKitResponse
import com.brent.weather.domain.model.WeatherData
import java.time.Instant
import java.time.format.DateTimeParseException

class WeatherKitMapper : WeatherMapper<WeatherKitResponse> {
    override fun mapToDomain(apiResponse: WeatherKitResponse): WeatherData {
        val current = apiResponse.currentWeather
        val epochSeconds = try {
            Instant.parse(current.asOf).epochSecond
        } catch (e: DateTimeParseException) {
            System.currentTimeMillis() / 1000
        }

        return WeatherData(
            locationName = "Apple WeatherKit Location", // WeatherKit REST API requires name to be supplied by caller or reverse-geocoded
            timestamp = epochSeconds,
            temperatureCelsius = current.temperature,
            feelsLikeCelsius = current.feelsLike,
            humidityPercentage = (current.humidity * 100).toInt(),
            windSpeedKph = current.windSpeed,
            conditionDescription = current.conditionCode,
            iconCode = mapConditionToIcon(current.conditionCode)
        )
    }

    private fun mapConditionToIcon(conditionCode: String): String {
        return when (conditionCode.lowercase()) {
            "clear", "mostlyclear" -> "01d"
            "cloudy", "mostlycloudy" -> "04d"
            "partlycloudy" -> "02d"
            "rain", "heavyrain", "sunshower" -> "10d"
            "drizzle", "lightrain" -> "09d"
            "snow", "heavysnow", "flurries" -> "13d"
            "thunderstorm" -> "11d"
            "fog", "haze" -> "50d"
            else -> "03d"
        }
    }
}
