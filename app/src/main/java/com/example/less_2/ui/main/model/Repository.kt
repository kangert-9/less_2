package com.example.less_2.ui.main.model

interface Repository {
    fun getFilmFromServer(): List<Film>
    fun getFilmFromLocalStorage(): List<Film>

}