package com.example.less_2.ui.main.model

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

private const val REQUEST_API_KEY = "X-themoviedb-API-Key"

class RemoteDataSource {
    fun getFilmDetails(requestLink: String, callback: Callback) {
        val builder: Request.Builder = Request.Builder().apply {
            header(REQUEST_API_KEY, "0e50ea7e6e840b382254d1a4b8d8c2a4")
            url(requestLink)
        }
        OkHttpClient().newCall(builder.build()).enqueue(callback)
    }
}