import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.brent.weather.data.api.HttpClientProvider
import com.brent.weather.data.mapper.OwmWeatherMapper
import com.brent.weather.data.mapper.WeatherKitMapper
import com.brent.weather.data.repository.OwmRepositoryImpl
import com.brent.weather.data.repository.WeatherKitRepositoryImpl
import com.brent.weather.ui.WeatherScreen
import com.brent.weather.ui.WeatherUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main() = application {
    var state by remember { mutableStateOf<WeatherUiState>(WeatherUiState.Loading) }
    
    // We can select the active repository (OWM by default)
    val owmRepository = remember {
        OwmRepositoryImpl(
            client = HttpClientProvider.client,
            mapper = OwmWeatherMapper(),
            apiKey = "YOUR_OPENWEATHERMAP_API_KEY" // Replace with user's OWM API Key
        )
    }
    
    val weatherKitRepository = remember {
        WeatherKitRepositoryImpl(
            client = HttpClientProvider.client,
            mapper = WeatherKitMapper(),
            tokenProvider = { "YOUR_WEATHERKIT_TOKEN" } // Replace with WeatherKit JWT token
        )
    }
    
    var useWeatherKit by remember { mutableStateOf(false) }
    val currentRepo = if (useWeatherKit) weatherKitRepository else owmRepository
    
    fun fetch() {
        state = WeatherUiState.Loading
        CoroutineScope(Dispatchers.IO).launch {
            currentRepo.getWeatherData(40.7128, -74.0060)
                .onSuccess { data ->
                    state = WeatherUiState.Success(data)
                }
                .onFailure { error ->
                    state = WeatherUiState.Error(error.message ?: "Unknown Error")
                }
        }
    }
    
    LaunchedEffect(useWeatherKit) {
        fetch()
    }
    
    Window(
        onCloseRequest = ::exitApplication,
        title = "Weather Mang - macOS Desktop"
    ) {
        androidx.compose.material3.MaterialTheme {
            androidx.compose.foundation.layout.Column {
                androidx.compose.material3.TabRow(selectedTabIndex = if (useWeatherKit) 1 else 0) {
                    androidx.compose.material3.Tab(
                        selected = !useWeatherKit,
                        onClick = { useWeatherKit = false },
                        text = { androidx.compose.material3.Text("OpenWeatherMap") }
                    )
                    androidx.compose.material3.Tab(
                        selected = useWeatherKit,
                        onClick = { useWeatherKit = true },
                        text = { androidx.compose.material3.Text("Apple WeatherKit") }
                    )
                }
                WeatherScreen(
                    state = state,
                    onRefresh = { fetch() },
                    modifier = androidx.compose.ui.Modifier.weight(1f)
                )
            }
        }
    }
}
