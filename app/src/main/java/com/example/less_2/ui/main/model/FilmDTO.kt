package com.example.less_2.ui.main.model

data class FilmDTO (
    val fact: FactDTO?
)

data class FactDTO(
    val original_title: String?,
    val budget: Int?,
)