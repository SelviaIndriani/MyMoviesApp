package com.slv.mysubmissionandroidexpert.core.domain.repository

import androidx.paging.PagedList
import com.slv.mysubmissionandroidexpert.core.data.source.Resource
import com.slv.mysubmissionandroidexpert.core.domain.model.Movie
import com.slv.mysubmissionandroidexpert.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface IMyMoviesRepository {

    fun getPopularMovie(): Flow<Resource<PagedList<Movie>>>

    fun getTopMovie(): Flow<Resource<PagedList<Movie>>>

    fun getDetailMovie(movieId: Int): Flow<Resource<Movie>>

    fun setFavoriteMovie(movie: Movie, state: Boolean)

    fun getFavoriteMovie(): Flow<PagedList<Movie>>


    fun getPopularTvShow(): Flow<Resource<PagedList<TvShow>>>

    fun getTopTvShow(): Flow<Resource<PagedList<TvShow>>>

    fun getDetailTvShow(tvId: Int): Flow<Resource<TvShow>>

    fun setFavoriteTv(tvShow: TvShow, state: Boolean)

    fun getFavoriteTv(): Flow<PagedList<TvShow>>


}