package com.example.less_2.ui.main.model


class RepositoryImpl : Repository
{

    override fun getFilmFromServer() = getFilms()

    override fun getFilmFromLocalStorage() = getFilms()

}