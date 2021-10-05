package com.example.less_2.ui.main.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.less_2.databinding.MainFragmentBinding
import com.example.less_2.ui.main.viewmodel.MainViewModel
import com.example.less_2.ui.main.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.less_2.ui.main.model.FilmAdapter
import com.example.less_2.ui.main.model.Repository


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

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
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getFilm()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                //val filmData = appState.filmData.toString()
                binding.loadingLayout.visibility = View.GONE
                initAdapter()
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.root, "Error: ${appState.error}", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getFilm() }
                    .show()
            }
        }
    }

    private fun initAdapter() {
        val recyclerView = binding.recyclerViewLines
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        val adapter = FilmAdapter()
        recyclerView.adapter = adapter

        adapter.setFilmList(Repository.filmList)
    }
}