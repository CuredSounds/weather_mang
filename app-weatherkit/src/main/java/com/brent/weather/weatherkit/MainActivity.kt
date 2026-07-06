package com.brent.weather.weatherkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.brent.weather.data.mapper.WeatherKitMapper
import com.brent.weather.data.repository.WeatherKitRepositoryImpl
import com.brent.weather.ui.WeatherScreen
import com.brent.weather.weatherkit.auth.WeatherKitAuth

class MainActivity : ComponentActivity() {

    // Simple manual injection using Ktor
    private val httpClient = com.brent.weather.data.api.HttpClientProvider.client
    private val mapper = WeatherKitMapper()
    
    private val repository = WeatherKitRepositoryImpl(
        client = httpClient,
        mapper = mapper,
        tokenProvider = { WeatherKitAuth.generateToken() }
    )
    private val viewModel = WeatherViewModel(repository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Fetch weather for a default location (e.g., NYC)
        viewModel.fetchWeather(40.7128, -74.0060)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by viewModel.uiState.collectAsState()
                    WeatherScreen(
                        state = state,
                        onRefresh = { viewModel.fetchWeather(40.7128, -74.0060) }
                    )
                }
            }
        }
    }
}
