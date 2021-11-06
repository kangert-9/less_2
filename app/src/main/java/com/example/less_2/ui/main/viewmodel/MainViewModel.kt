package com.example.less_2.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.less_2.ui.main.model.Film
import com.example.less_2.ui.main.model.Repository
import com.example.less_2.ui.main.model.RepositoryImpl
import com.example.less_2.ui.main.model.app.App.Companion.getHistoryDao
import com.example.less_2.ui.main.model.app.LocalRepository
import com.example.less_2.ui.main.model.app.LocalRepositoryImpl
import java.lang.Thread.sleep
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repositoryImpl: Repository = RepositoryImpl()
    fun getLiveData() = liveDataToObserve
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())

    fun getFilm() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            if(Random.nextBoolean()) {
                liveDataToObserve.postValue(AppState.Success(repositoryImpl.getFilmFromLocalStorage()))
            } else {
                liveDataToObserve.postValue(AppState.Error(Exception("нет интернета")))
            }
        }.start()
    }

    fun saveFilmToDB(film: Film) {
        historyRepository.saveEntity(film)
    }

}
