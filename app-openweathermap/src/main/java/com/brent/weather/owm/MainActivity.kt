package com.brent.weather.owm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.brent.weather.data.mapper.OwmWeatherMapper
import com.brent.weather.data.repository.OwmRepositoryImpl
import com.brent.weather.ui.WeatherScreen

class MainActivity : ComponentActivity() {

    // Simple manual injection using Ktor
    private val httpClient = com.brent.weather.data.api.HttpClientProvider.client
    private val mapper = OwmWeatherMapper()
    
    // TODO: Replace with user OWM API Key
    private val apiKey = "YOUR_OPENWEATHERMAP_API_KEY"

    private val repository = OwmRepositoryImpl(httpClient, mapper, apiKey)
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
