package com.example.less_2.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.less_2.databinding.FragmentFilmBinding
import com.example.less_2.ui.main.model.Film
import com.example.less_2.ui.main.viewmodel.AppState
import com.example.less_2.ui.main.viewmodel.MainViewModel

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
private const val MAIN_LINK = "https://api.themoviedb.org/3/movie/"

class FilmFragment : Fragment() {
    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmBundle: Film
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Film(filmBundle.id,filmBundle.name, filmBundle.rating, filmBundle.director, filmBundle.year, filmBundle.isLike)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getFilmFromRemoteSource(MAIN_LINK + "${filmBundle.id}?api_key=0e50ea7e6e840b382254d1a4b8d8c2a4")

}

    private fun renderData(appState: AppState) {
//        val title = filmDTO.original_title
//        val overview = filmDTO.overview
//        if (title == TITLE_INVALID || overview == OVERVIEW_INVALID) {
//            TODO(PROCESS_ERROR)
//        } else {
//            name_film.text = title.toString()
//            description_film.text = overview.toString()
//        }
        when (appState) {
            is AppState.Success -> {
                setFilm(appState.filmData[0])
            }
            is AppState.Error -> {
                //TODO
            }
            else -> {
                //TODO
            }
        }
    }

    private fun setFilm(s: Film) {
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