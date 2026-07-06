package com.brent.weather.data.repository

import com.brent.weather.data.mapper.OwmWeatherMapper
import com.brent.weather.data.model.OwmResponse
import com.brent.weather.domain.model.WeatherData
import com.brent.weather.domain.repository.WeatherRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class OwmRepositoryImpl(
    private val client: HttpClient,
    private val mapper: OwmWeatherMapper,
    private val apiKey: String
) : WeatherRepository {

    override suspend fun getWeatherData(lat: Double, lon: Double): Result<WeatherData> {
        return try {
            val response: OwmResponse = client.get("https://api.openweathermap.org/data/2.5/weather") {
                parameter("lat", lat)
                parameter("lon", lon)
                parameter("appid", apiKey)
                parameter("units", "metric")
            }.body()
            val domainData = mapper.mapToDomain(response)
            Result.success(domainData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
