package com.slv.favorite.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.slv.mysubmissionandroidexpert.core.domain.usecase.MyMoviesUseCase

class FavoriteTvShowViewModel(myMoviesUseCase: MyMoviesUseCase) : ViewModel() {

    val getFavoriteTvShow = myMoviesUseCase.getFavoriteTv().asLiveData()
}