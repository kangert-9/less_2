package com.example.less_2.ui.main.view

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.less_2.databinding.FragmentFilmBinding
import com.example.less_2.ui.main.model.Film
import com.example.less_2.ui.main.model.FilmDTO
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_film.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

//private const val YOUR_API_KEY = "0e50ea7e6e840b382254d1a4b8d8c2a4"

class FilmFragment : Fragment() {
    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmBundle: Film


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Film(filmBundle.id,filmBundle.name, filmBundle.rating, filmBundle.director, filmBundle.year, filmBundle.isLike)

        Thread {
        loadFilm(
            url =  URL("https://api.themoviedb.org/3/movie/${filmBundle.id}?api_key=0e50ea7e6e840b382254d1a4b8d8c2a4&language=en-US")
        )}.start()
    }

    private fun displayFilm (filmDTO: FilmDTO) {
        name_film.text = filmBundle.name//filmDTO.fact?.original_title.toString()
        description_film.text = filmDTO.fact?.budget.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadFilm(url: URL) {
        var urlConnection: HttpsURLConnection? = null
        try {
            val handler = Handler(Looper.getMainLooper())
                try {
                    urlConnection = url.openConnection() as HttpsURLConnection
                    urlConnection.apply {
                        requestMethod = "GET"
                        readTimeout = 10000
                        addRequestProperty(
                            "X-Film-API-Key",
                            "0e50ea7e6e840b382254d1a4b8d8c2a4"
                        )
                    }
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))

                    val filmDTO: FilmDTO =
                        Gson().fromJson(getLines(bufferedReader), FilmDTO::class.java)
                    handler.post { displayFilm(filmDTO) }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                } finally {
                    urlConnection!!.disconnect()
                }
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    companion object {

        const val BUNDLE_EXTRA = "film"

        fun newInstance(bundle: Bundle): FilmFragment {
            val fragment = FilmFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}