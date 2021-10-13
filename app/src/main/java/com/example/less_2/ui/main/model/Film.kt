package com.example.less_2.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val id: Int?, val name: String?, val rating: Double?, val director: String?,
    val year: Int?, val isLike: Boolean?) : Parcelable


fun getFilms() = listOf(
        Film(580489,"Venom 2",7.1, "Andy Serkis", 2021, false),
        Film(438631,"Dune", 8.0, "Denis Villeneuve", 2021, false),
        Film(577922,"Tenet",9.6, "Christopher Jonathan James Nolan", 2020, false),
        Film(497698,"Black Widow", 5.5, "Cate Shortland", 2021, false),
        Film(370172,"James Bond. No time to die", 9.0, "Cary Joji Fukunaga", 2021, false)
    )
