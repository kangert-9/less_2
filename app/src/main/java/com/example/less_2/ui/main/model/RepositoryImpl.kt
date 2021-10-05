package com.example.less_2.ui.main.model


object Repository
 {
    val filmList: List<Film> = listOf(
        Film("Venom 2",7.1),
        Film("Dune", 8.0),
        Film("Tenet",9.6),
        Film("Black Widow", 5.5),
        Film("James Bond. No time to die", 9.0)
    )

    fun getFilmFromServer(): List<Film> {
        return filmList
    }

    fun getFilmFromLocalStorage(): List<Film> {
        return filmList
    }
}