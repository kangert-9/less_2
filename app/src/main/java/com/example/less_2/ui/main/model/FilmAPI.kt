package com.example.less_2.ui.main.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FilmAPI {
    @GET("v2/informers")
    fun getWeather(
        @Header("X-themoviedb-API-Key") token: String,
        @Query("id") id: Int
    ): Call<FilmDTO>
}