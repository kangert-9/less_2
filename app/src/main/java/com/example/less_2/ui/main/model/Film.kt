package com.example.less_2.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(val name: String, val rating: Double, val director: String,
                val year: Int, val isLike: Boolean) : Parcelable


fun getFilms() = listOf(
        Film("Venom 2",7.1, "Andy Serkis", 2021, false),
        Film("Dune", 8.0, "Denis Villeneuve", 2021, false),
        Film("Tenet",9.6, "Christopher Jonathan James Nolan", 2020, false),
        Film("Black Widow", 5.5, "Cate Shortland", 2021, false),
        Film("James Bond. No time to die", 9.0, "Cary Joji Fukunaga", 2021, false)
    )
