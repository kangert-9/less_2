package com.example.less_2.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmDTO (
    val original_title: String?,
    val overview: String?
) : Parcelable