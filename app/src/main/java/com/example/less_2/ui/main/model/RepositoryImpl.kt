package com.example.less_2.ui.main.model


class RepositoryImpl : Repository
{
//    val filmList: List<Film> = listOf(
//        Film("Venom 2",7.1, "Andy Serkis", 2021, false),
//        Film("Dune", 8.0, "Denis Villeneuve", 2021, false),
//        Film("Tenet",9.6, "Christopher Jonathan James Nolan", 2020, false),
//        Film("Black Widow", 5.5, "Cate Shortland", 2021, false),
//        Film("James Bond. No time to die", 9.0, "Cary Joji Fukunaga", 2021, false)
//    )

    override fun getFilmFromServer(): List<Film> {
        return getFilms()
    }

    override fun getFilmFromLocalStorage(): List<Film> {
        return getFilms()
    }
}