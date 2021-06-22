package com.slv.favorite.tvshow

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.slv.favorite.databinding.FragmentFavoriteTvShowBinding
import com.slv.favorite.di.DaggerFavoriteComponent
import com.slv.favorite.viewmodel.ViewModelFactory
import com.slv.mysubmissionandroidexpert.presentation.di.FavoriteDependencies
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject


class FavoriteTvShowFragment : Fragment() {

    private var _binding: FragmentFavoriteTvShowBinding? = null
    private val binding get() = _binding as FragmentFavoriteTvShowBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteTvShowViewModel: FavoriteTvShowViewModel by viewModels { factory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentFavoriteTvShowBinding.inflate(layoutInflater, container, false)
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
            val favoriteTvShowAdapter = FavoriteTvShowAdapter()

            loadProgress(true)
            favoriteTvShowViewModel.getFavoriteTvShow.observe(viewLifecycleOwner, { tv ->
                if (tv != null) {
                    favoriteTvShowAdapter.setFavTvShow(tv)
                    loadProgress(false)
                    favoriteTvShowAdapter.submitList(tv)
                    binding.emptyLayout.root.visibility = if (tv.isEmpty()) View.VISIBLE else View.GONE
                }
            })

            with(binding.rvFavTvShow) {
                this.layoutManager = LinearLayoutManager(context)
                this.setHasFixedSize(true)
                this.adapter = favoriteTvShowAdapter
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