package com.slv.mysubmissionandroidexpert.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.slv.mysubmissionandroidexpert.core.domain.usecase.MyMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val myMoviesUseCase: MyMoviesUseCase) :
    ViewModel() {

    private var id = MutableLiveData<Int>()

    fun setSelectedId(id: Int) {
        this.id.value = id
    }

    var getMovie = Transformations.switchMap(id) { id ->
        myMoviesUseCase.getDetailMovie(id).asLiveData()
    }

    var getTvShow = Transformations.switchMap(id) { id ->
        myMoviesUseCase.getDetailTvShow(id).asLiveData()
    }

    fun setMoviesFav() {
        val movieData = getMovie.value
        if (movieData != null) {
            val movie = movieData.data

            if (movie != null) {
                val isFav = !movie.isFavorite
                myMoviesUseCase.setFavoriteMovie(movie, isFav)
            }
        }
    }

    fun setTvShowFav() {
        val tvData = getTvShow.value
        if (tvData != null) {
            val tvShow = tvData.data

            if (tvShow != null) {
                val isFav = !tvShow.isFavorite
                myMoviesUseCase.setFavoriteTv(tvShow, isFav)
            }
        }
    }
}