package com.example.less_2.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.less_2.ui.main.model.*
import com.google.gson.Gson
import okhttp3.Call
import java.io.IOException
import okhttp3.Callback
import okhttp3.Response


private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class MainViewModel (
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl(RemoteDataSource())
) : ViewModel() {
    fun getLiveData() = liveData

    fun getFilmFromRemoteSource(requestLink: String) {
        liveData.value = AppState.Loading
        repositoryImpl.getFilmFromServer(requestLink, callBack)
    }

    private val callBack = object : Callback {

        @Throws(IOException::class)
        override fun onResponse(call: Call?, response: Response) {
            val serverResponse: String? = response.body()?.string()
            liveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call?, e: IOException?) {
            liveData.postValue(AppState.Error(Throwable(e?.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(serverResponse: String): AppState {
            val filmDTO: FilmDTO =
                Gson().fromJson(serverResponse, FilmDTO::class.java)
            return if (filmDTO.original_title == null || filmDTO.overview == null) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.Success(convertDtoToModel(filmDTO))
            }
        }
    }

    fun convertDtoToModel(filmDTO: FilmDTO): List<Film> {
        return listOf(Film(0, filmDTO.original_title, 0.0, filmDTO.overview, 2021, false))
    }
}
