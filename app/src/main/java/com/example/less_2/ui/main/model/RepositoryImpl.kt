package com.example.less_2.ui.main.model


class RepositoryImpl : Repository
{
    override fun getFilmFromServer(): List<Film> {
        return getFilms()
    }

    override fun getFilmFromLocalStorage(): List<Film> {
        return getFilms()
    }
}