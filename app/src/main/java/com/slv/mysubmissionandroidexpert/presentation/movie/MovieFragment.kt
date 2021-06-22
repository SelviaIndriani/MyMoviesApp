package com.slv.mysubmissionandroidexpert.presentation.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.slv.mysubmissionandroidexpert.core.data.source.Resource
import com.slv.mysubmissionandroidexpert.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModels()
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding as FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val listMovieAdapter = ListMovieAdapter()
            val viewMovieAdapter = ViewMovieAdapter()

            movieViewModel.dataMovie.observe(viewLifecycleOwner, { movies ->
                if (movies != null) {
                    when(movies){
                        is Resource.Loading -> { loadProgress(true)}
                        is Resource.Success -> {
                            listMovieAdapter.setMovie(movies.data)
                            loadProgress(false)
                            listMovieAdapter.submitList(movies.data)
                        }
                        is Resource.Error -> {
                            loadProgress(false)
                            binding.noConnection.root.visibility = View.VISIBLE
                        }
                    }
                }
            })

            movieViewModel.dataTopMovie.observe(viewLifecycleOwner, { movies ->
                if (movies != null) {
                    when(movies){
                        is Resource.Loading -> { loadProgress2(true)}
                        is Resource.Success -> {
                            viewMovieAdapter.setViewMovie(movies.data)
                            loadProgress2(false)
                            viewMovieAdapter.submitList(movies.data)
                        }
                        is Resource.Error -> {
                            loadProgress2(false)
                            binding.noConnection.root.visibility = View.VISIBLE
                        }
                    }
                }
            })

            with(binding.rvPosterMovie) {
                this.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                this.setHasFixedSize(true)
                this.adapter = listMovieAdapter
            }

            with(binding.rvListMovie) {
                this.layoutManager = LinearLayoutManager(context)
                this.setHasFixedSize(true)
                this.adapter = viewMovieAdapter
            }
        }
    }

    private fun loadProgress(load: Boolean) {
        if (load) {
            binding.progressBar.visibility =  View.VISIBLE
            binding.rvPosterMovie.visibility = View.GONE
        } else{
            binding.progressBar.visibility =  View.GONE
            binding.rvPosterMovie.visibility = View.VISIBLE
        }
    }

    private fun loadProgress2(load: Boolean) {
        if (load) {
            binding.progressBar2.visibility =  View.VISIBLE
            binding.rvListMovie.visibility = View.GONE
        } else{
            binding.progressBar2.visibility =  View.GONE
            binding.rvListMovie.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}