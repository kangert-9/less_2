package com.example.less_2.ui.main.model.app

import com.example.less_2.ui.main.model.Film

interface LocalRepository {
    fun getAllHistory(): List<Film>
    fun saveEntity(film: Film)
}