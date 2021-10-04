package com.example.less_2.ui.main.model

import com.example.less_2.ui.main.model.Repository

class RepositoryImpl : Repository {

    override fun getWeatherFromServer(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorage(): Weather {
        return Weather()
    }
}