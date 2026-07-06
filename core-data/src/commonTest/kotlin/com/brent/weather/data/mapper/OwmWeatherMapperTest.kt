package com.brent.weather.data.mapper

import com.brent.weather.data.model.OwmMain
import com.brent.weather.data.model.OwmResponse
import com.brent.weather.data.model.OwmWeather
import com.brent.weather.data.model.OwmWind
import kotlin.test.Test
import kotlin.test.assertEquals

class OwmWeatherMapperTest {

    @Test
    fun testMapToDomain() {
        val mapper = OwmWeatherMapper()
        val mockResponse = OwmResponse(
            cityName = "New York",
            timestamp = 1625392800L,
            main = OwmMain(temp = 25.5, feelsLike = 26.0, humidity = 60),
            wind = OwmWind(speed = 5.0),
            weather = listOf(OwmWeather(description = "scattered clouds", icon = "03d"))
        )

        val domain = mapper.mapToDomain(mockResponse)

        assertEquals("New York", domain.locationName)
        assertEquals(1625392800L, domain.timestamp)
        assertEquals(25.5, domain.temperatureCelsius, 0.01)
        assertEquals(26.0, domain.feelsLikeCelsius, 0.01)
        assertEquals(60, domain.humidityPercentage)
        assertEquals(18.0, domain.windSpeedKph, 0.01) // 5.0 m/s * 3.6
        assertEquals("scattered clouds", domain.conditionDescription)
        assertEquals("03d", domain.iconCode)
    }
}
