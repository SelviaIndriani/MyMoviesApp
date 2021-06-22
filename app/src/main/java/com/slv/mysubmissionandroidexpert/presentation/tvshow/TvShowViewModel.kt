package com.slv.mysubmissionandroidexpert.presentation.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.slv.mysubmissionandroidexpert.core.domain.usecase.MyMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(myMoviesUseCase: MyMoviesUseCase) : ViewModel() {
    val dataTvShow = myMoviesUseCase.getPopularTvShow().asLiveData()

    val dataTopTvShow = myMoviesUseCase.getTopTvShow().asLiveData()
}