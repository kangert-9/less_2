package com.example.less_2.ui.main.viewmodel

import com.example.less_2.ui.main.model.Weather

sealed class AppState {
    data class Success(val weatherData: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}