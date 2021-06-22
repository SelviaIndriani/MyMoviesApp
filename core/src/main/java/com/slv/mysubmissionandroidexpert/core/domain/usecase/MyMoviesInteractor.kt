package com.slv.mysubmissionandroidexpert.core.domain.usecase

import com.slv.mysubmissionandroidexpert.core.domain.model.Movie
import com.slv.mysubmissionandroidexpert.core.domain.model.TvShow
import com.slv.mysubmissionandroidexpert.core.domain.repository.IMyMoviesRepository
import javax.inject.Inject

class MyMoviesInteractor @Inject constructor(private val myMoviesRepository: IMyMoviesRepository) :
    MyMoviesUseCase {
    override fun getPopularMovie() = myMoviesRepository.getPopularMovie()

    override fun getTopMovie() = myMoviesRepository.getTopMovie()

    override fun getDetailMovie(movieId: Int) = myMoviesRepository.getDetailMovie(movieId)

    override fun setFavoriteMovie(movie: Movie, state: Boolean) =
        myMoviesRepository.setFavoriteMovie(movie, state)

    override fun getFavoriteMovie() = myMoviesRepository.getFavoriteMovie()


    override fun getPopularTvShow() = myMoviesRepository.getPopularTvShow()

    override fun getTopTvShow() = myMoviesRepository.getTopTvShow()

    override fun getDetailTvShow(tvId: Int) = myMoviesRepository.getDetailTvShow(tvId)

    override fun setFavoriteTv(tvShow: TvShow, state: Boolean) =
        myMoviesRepository.setFavoriteTv(tvShow, state)

    override fun getFavoriteTv() = myMoviesRepository.getFavoriteTv()
}