package com.example.less_2.ui.main.model

interface Repository {
    fun getFilmFromServer(): Film
    fun getFilmFromLocalStorage(): Film
}