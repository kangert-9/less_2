package com.example.less_2.ui.main.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(

    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val rating: Double,
    val director: String?,
    val year: Int?,
    val isLike: Boolean?
)