package com.example.less_2.ui.main.viewmodel

import com.example.less_2.ui.main.model.Film


sealed class AppState {
    data class Success(val filmData: List<Film>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}