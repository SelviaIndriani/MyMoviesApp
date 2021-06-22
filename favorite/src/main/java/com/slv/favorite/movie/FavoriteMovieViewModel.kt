package com.slv.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.slv.mysubmissionandroidexpert.core.domain.usecase.MyMoviesUseCase

class FavoriteMovieViewModel(myMoviesUseCase: MyMoviesUseCase) : ViewModel() {

    val getFavoriteMovie = myMoviesUseCase.getFavoriteMovie().asLiveData()
}