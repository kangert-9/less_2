package com.example.less_2.ui.main.model.app

import com.example.less_2.ui.main.model.Film
import com.example.less_2.ui.main.model.database.HistoryDao
import com.example.less_2.ui.main.model.database.HistoryEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDao) :
    LocalRepository {

    override fun getAllHistory(): List<Film> {
        return convertHistoryEntityToFilm(localDataSource.all())
    }

    override fun saveEntity(film: Film) {
        localDataSource.insert(convertFilmToEntity(film))
    }

    private fun convertHistoryEntityToFilm(entityList: List<HistoryEntity>):
            List<Film> {
        return entityList.map {
            Film(0, "name", 0.0, "director", 2000, false)
        }
    }

    private fun convertFilmToEntity(film: Film): HistoryEntity {
        return HistoryEntity(film.id!!.toInt(), film.name.toString(), film.rating!!.toDouble(),film.director.toString(), film.year!!.toInt(), film.isLike)
    }
    }