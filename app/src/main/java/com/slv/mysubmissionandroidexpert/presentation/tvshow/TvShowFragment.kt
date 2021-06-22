package com.slv.mysubmissionandroidexpert.presentation.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.slv.mysubmissionandroidexpert.core.data.source.Resource
import com.slv.mysubmissionandroidexpert.databinding.FragmentTvShowBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowFragment : Fragment() {
    private val tvShowViewModel: TvShowViewModel by viewModels()
    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding as FragmentTvShowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val listTvShowAdapter = ListTvShowAdapter()
            val viewTvShowAdapter = ViewTvShowAdapter()

            tvShowViewModel.dataTvShow.observe(viewLifecycleOwner, { tvShow ->
                if (tvShow != null) {
                    when(tvShow){
                        is Resource.Loading -> { loadProgress(true)}
                        is Resource.Success -> {
                            listTvShowAdapter.setTvShow(tvShow.data)
                            loadProgress(false)
                            listTvShowAdapter.submitList(tvShow.data)
                        }
                        is Resource.Error -> {
                            loadProgress(false)
                            binding.noConnection.root.visibility = View.VISIBLE
                        }
                    }
                }
            })

            tvShowViewModel.dataTopTvShow.observe(viewLifecycleOwner, { tvShow ->
                if (tvShow != null) {
                    when(tvShow){
                        is Resource.Loading -> { loadProgress2(true)}
                        is Resource.Success -> {
                            viewTvShowAdapter.setViewTvShow(tvShow.data)
                            loadProgress2(false)
                            viewTvShowAdapter.submitList(tvShow.data)
                        }
                        is Resource.Error -> {
                            loadProgress2(false)
                            binding.noConnection.root.visibility = View.VISIBLE
                        }
                    }
                }
            })

            with(binding.rvPosterTvShows) {
                this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                this.setHasFixedSize(true)
                this.adapter = listTvShowAdapter
            }

            with(binding.rvListTvShows) {
                this.layoutManager = LinearLayoutManager(context)
                this.setHasFixedSize(true)
                this.adapter = viewTvShowAdapter
            }
        }
    }

    private fun loadProgress(load: Boolean) {
        if (load) {
            binding.progressBar.visibility =  View.VISIBLE
            binding.rvPosterTvShows.visibility = View.GONE
        } else{
            binding.progressBar.visibility =  View.GONE
            binding.rvPosterTvShows.visibility = View.VISIBLE
        }
    }

    private fun loadProgress2(load: Boolean) {
        if (load) {
            binding.progressBar2.visibility =  View.VISIBLE
            binding.rvListTvShows.visibility = View.GONE
        } else{
            binding.progressBar2.visibility =  View.GONE
            binding.rvListTvShows.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}