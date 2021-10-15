package com.example.less_2.ui.main.model

import okhttp3.Callback

interface Repository {
    fun getFilmFromServer(requestLink: String, callback: Callback)

}