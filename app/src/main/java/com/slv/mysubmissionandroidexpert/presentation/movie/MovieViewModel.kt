package com.slv.mysubmissionandroidexpert.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.slv.mysubmissionandroidexpert.core.domain.usecase.MyMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(myMoviesUseCase: MyMoviesUseCase) : ViewModel() {
    val dataMovie = myMoviesUseCase.getPopularMovie().asLiveData()

    val dataTopMovie = myMoviesUseCase.getTopMovie().asLiveData()
}