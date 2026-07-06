package com.brent.weather.weatherkit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brent.weather.domain.repository.WeatherRepository
import com.brent.weather.ui.WeatherUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            repository.getWeatherData(lat, lon)
                .onSuccess { data ->
                    _uiState.value = WeatherUiState.Success(data)
                }
                .onFailure { error ->
                    _uiState.value = WeatherUiState.Error(error.message ?: "Unknown Error")
                }
        }
    }
}
