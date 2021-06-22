package com.slv.favorite.movie

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.slv.favorite.databinding.FragmentFavoriteMovieBinding
import com.slv.favorite.di.DaggerFavoriteComponent
import com.slv.favorite.viewmodel.ViewModelFactory
import com.slv.mysubmissionandroidexpert.presentation.di.FavoriteDependencies
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteMovieFragment : Fragment() {

    private var _binding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _binding as FragmentFavoriteMovieBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteMovieViewModel: FavoriteMovieViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder()
            .context(context)
            .favoriteDependencies(
                EntryPointAccessors.fromApplication(
                    context.applicationContext, FavoriteDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val favoriteMovieAdapter = FavoriteMovieAdapter()

            loadProgress(true)
            favoriteMovieViewModel.getFavoriteMovie.observe(viewLifecycleOwner, { movies ->
                if (movies != null) {
                    favoriteMovieAdapter.setFavMovie(movies)
                    loadProgress(false)
                    favoriteMovieAdapter.submitList(movies)
                    binding.emptyLayout.root.visibility =
                        if (movies.isEmpty()) View.VISIBLE else View.GONE
                }
            })

            with(binding.rvFavMovie) {
                this.layoutManager = LinearLayoutManager(context)
                this.setHasFixedSize(true)
                this.adapter = favoriteMovieAdapter
            }
        }


    }

    private fun loadProgress(load: Boolean) {
        binding.progressBar.visibility = if (load) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}