package com.example.less_2.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.less_2.databinding.FragmentFilmBinding
import com.example.less_2.ui.main.model.Film


class FilmFragment : Fragment() {
    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val film = arguments?.getParcelable<Film>(BUNDLE_EXTRA)
        if (film != null) {
            val s = "Director: " + film.director + " Year: "+ film.year+ " Rating: "+ film.rating
            val name = film.name
            binding.nameFilm.text = name
            binding.descriptionFilm.text = s
        }
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