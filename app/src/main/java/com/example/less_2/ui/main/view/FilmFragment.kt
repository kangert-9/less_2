package com.example.less_2.ui.main.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.less_2.databinding.FragmentFilmBinding
import com.example.less_2.ui.main.model.Film
import com.example.less_2.ui.main.model.FilmDTO
import com.example.less_2.ui.main.model.MainService
import kotlinx.android.synthetic.main.fragment_film.*

const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val ID_EXTRA = "ID_Extra"
const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
const val DETAILS_TITLE_EXTRA = "TITLE"
const val DETAILS_OVERVIEW_EXTRA = "OVERVIEW"
private const val TITLE_INVALID = "-100"
private const val OVERVIEW_INVALID: String = "-100"
private const val PROCESS_ERROR = "Обработка ошибки"

class FilmFragment : Fragment() {
    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmBundle: Film
    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_DATA_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> renderData(
                    FilmDTO(
                        intent.getStringExtra(DETAILS_TITLE_EXTRA),
                        intent.getStringExtra(DETAILS_OVERVIEW_EXTRA),
                    )
                )
                else -> TODO(PROCESS_ERROR)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
        }
    }


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
        getFilm()
    }

    private fun getFilm() {
        context?.let {
            it.startService(Intent(it, MainService::class.java).apply {
                putExtra(
                    ID_EXTRA,
                    filmBundle.id
                )
            })
        }
    }

    private fun renderData(filmDTO: FilmDTO) {
        val title = filmDTO.original_title
        val overview = filmDTO.overview
        if (title == TITLE_INVALID || overview == OVERVIEW_INVALID) {
            TODO(PROCESS_ERROR)
        } else {
            name_film.text = title.toString()
            description_film.text = overview.toString()
        }
    }

    override fun onDestroy() {
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
        super.onDestroy()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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