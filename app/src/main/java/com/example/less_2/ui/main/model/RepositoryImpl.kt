package com.example.less_2.ui.main.model

import okhttp3.Callback

class RepositoryImpl (private val remoteDataSource: RemoteDataSource) :
    Repository {

    override fun getFilmFromServer(requestLink: String, callback: Callback) {
        remoteDataSource.getFilmDetails(requestLink, callback)
    }
}
