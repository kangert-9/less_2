package com.example.less_2.ui.main.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.less_2.R
import com.example.less_2.databinding.MainFragmentBinding
import com.example.less_2.ui.main.model.Film
import com.example.less_2.ui.main.viewmodel.MainViewModel
import com.example.less_2.ui.main.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

import com.example.less_2.ui.main.model.FilmAdapter


class MainFragment : Fragment() {
    interface OnItemViewClickListener {
        fun onItemViewClick(film: Film)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private val adapter = FilmAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(film: Film) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .replace(R.id.container, FilmFragment.newInstance(Bundle().apply {
                        putParcelable(FilmFragment.BUNDLE_EXTRA, film)
                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
       // viewModel.getFilm()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                val recyclerView = binding.recyclerViewLines
                val layoutManager = LinearLayoutManager(context)
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = adapter
                adapter.setFilmList(appState.filmData)
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.root, "Error: ${appState.error}", Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Reload") { viewModel.getFilm() }
                    .show()
            }
        }
    }
    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }
}