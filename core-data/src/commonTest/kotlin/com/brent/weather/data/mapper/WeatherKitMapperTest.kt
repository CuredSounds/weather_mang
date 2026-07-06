package com.brent.weather.data.mapper

import com.brent.weather.data.model.WkCurrentWeather
import com.brent.weather.data.model.WeatherKitResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class WeatherKitMapperTest {

    @Test
    fun testMapToDomain() {
        val mapper = WeatherKitMapper()
        val mockResponse = WeatherKitResponse(
            currentWeather = WkCurrentWeather(
                asOf = "2026-07-04T18:00:00Z",
                temperature = 22.0,
                feelsLike = 21.0,
                humidity = 0.55,
                windSpeed = 15.0,
                conditionCode = "Clear"
            )
        )

        val domain = mapper.mapToDomain(mockResponse)

        assertEquals("Apple WeatherKit Location", domain.locationName)
        assertEquals(22.0, domain.temperatureCelsius, 0.01)
        assertEquals(21.0, domain.feelsLikeCelsius, 0.01)
        assertEquals(55, domain.humidityPercentage)
        assertEquals(15.0, domain.windSpeedKph, 0.01)
        assertEquals("Clear", domain.conditionDescription)
        assertEquals("01d", domain.iconCode)
    }
}
