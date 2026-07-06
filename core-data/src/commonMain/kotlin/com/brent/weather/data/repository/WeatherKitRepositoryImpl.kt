package com.brent.weather.data.repository

import com.brent.weather.data.mapper.WeatherKitMapper
import com.brent.weather.data.model.WeatherKitResponse
import com.brent.weather.domain.model.WeatherData
import com.brent.weather.domain.repository.WeatherRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter

class WeatherKitRepositoryImpl(
    private val client: HttpClient,
    private val mapper: WeatherKitMapper,
    private val tokenProvider: () -> String
) : WeatherRepository {

    override suspend fun getWeatherData(lat: Double, lon: Double): Result<WeatherData> {
        return try {
            val token = tokenProvider()
            val language = "en-US"
            val response: WeatherKitResponse = client.get("https://weatherkit.apple.com/api/v1/weather/$language/$lat/$lon") {
                header("Authorization", "Bearer $token")
                parameter("dataSets", "currentWeather")
            }.body()
            val domainData = mapper.mapToDomain(response)
            Result.success(domainData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
