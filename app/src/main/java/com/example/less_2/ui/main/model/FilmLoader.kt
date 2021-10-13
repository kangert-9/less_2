package com.example.less_2.ui.main.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

@RequiresApi(Build.VERSION_CODES.N)
class FilmLoader (private val listener: FilmLoaderListener, private val id: Int) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadFilm() {
        Thread{
            val url = URL("https://api.themoviedb.org/3/movie/${id}?api_key=0e50ea7e6e840b382254d1a4b8d8c2a4")
            var urlConnection: HttpsURLConnection? = null
            try {
                val handler = Handler(Looper.getMainLooper())
                try {
                    urlConnection = url.openConnection() as HttpsURLConnection
                    urlConnection.apply {
                        requestMethod = "GET"
                        readTimeout = 10000
                        addRequestProperty(
                            "X-themoviedb-API-Key",
                            "0e50ea7e6e840b382254d1a4b8d8c2a4"
                        )
                    }
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val result = bufferedReader.lines().collect(Collectors.joining("\n"))

                    val filmDTO: FilmDTO =
                        Gson().fromJson(result, FilmDTO::class.java)
                    handler.post { listener.onLoaded(filmDTO) }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                } finally {
                    urlConnection!!.disconnect()
                }
            } catch (e: MalformedURLException) {
                listener.onFailed(e)
            }
        }.start()
    }

    interface FilmLoaderListener {
        fun onLoaded(filmDTO: FilmDTO)
        fun onFailed(throwable: Throwable)
    }
}