package com.example.less_2.ui.main.model

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.less_2.ui.main.view.*
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val MAIN_SERVICE_STRING_EXTRA = "MainServiceExtra"
private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000
private const val REQUEST_API_KEY = "X-themoviedb-API-Key"


class MainService(name: String = "MainService"): IntentService(name) {
    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        Log.d("MainService", "onHandleIntent ${intent?.getStringExtra(MAIN_SERVICE_STRING_EXTRA)}")
        intent?.let{
            val id = intent.getIntExtra(ID_EXTRA, 0)
            if (id==0){
                onEmptyData()
            } else{
                loadFilm(id)
            }
        } ?: onEmptyIntent()
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadFilm(id: Int) {
        var urlConnection: HttpsURLConnection? = null
        val url = try {
            URL("https://api.themoviedb.org/3/movie/${id}?api_key=0e50ea7e6e840b382254d1a4b8d8c2a4")
        } catch (e: MalformedURLException){
            onMalformedURL()
            return
        }
        try {
            try {
                urlConnection = url.openConnection() as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = REQUEST_GET
                    readTimeout = REQUEST_TIMEOUT
                    addRequestProperty(
                        REQUEST_API_KEY,
                        "0e50ea7e6e840b382254d1a4b8d8c2a4"
                    )
                }
                val bufferedReader =
                    BufferedReader(InputStreamReader(urlConnection.inputStream))
                val result = bufferedReader.lines().collect(Collectors.joining("\n"))
                val filmDTO: FilmDTO =
                    Gson().fromJson(result, FilmDTO::class.java)
                onResponse(filmDTO)
               // handler.post { listener.onLoaded(filmDTO) }
            } catch (e: Exception) {
                Log.e("", "Fail connection", e)
                e.printStackTrace()
            }
        } catch (e: MalformedURLException) {
            onErrorResponse(e.message ?:"Unknown Error")
        } finally {
            urlConnection!!.disconnect()
        }
    }

    private fun onMalformedURL() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorResponse(e: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, e)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onResponse(filmDTO: FilmDTO) {
        filmDTO?.let {
            onSuccessResponse(it)
        } ?: onEmptyResponse()
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(detailsResponseSuccessExtra: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, detailsResponseSuccessExtra)
    }

    private fun onSuccessResponse(it: FilmDTO) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(DETAILS_TITLE_EXTRA, it.original_title)
        broadcastIntent.putExtra(DETAILS_OVERVIEW_EXTRA, it.overview)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyData() {
        putLoadResult(DETAILS_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }
}