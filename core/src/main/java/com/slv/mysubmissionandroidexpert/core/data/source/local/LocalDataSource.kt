package com.slv.mysubmissionandroidexpert.core.data.source.local

import androidx.paging.DataSource
import com.slv.mysubmissionandroidexpert.core.data.source.local.entity.MovieEntity
import com.slv.mysubmissionandroidexpert.core.data.source.local.entity.TvShowEntity
import com.slv.mysubmissionandroidexpert.core.data.source.local.room.MyMoviesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val myMoviesDao: MyMoviesDao) {

    fun getMovies(): DataSource.Factory<Int, MovieEntity> = myMoviesDao.getMovie()

    fun getTopMovies(): DataSource.Factory<Int, MovieEntity> = myMoviesDao.getTopMovie()

    fun getMovieById(movieId: Int): Flow<MovieEntity> = myMoviesDao.getMovieById(movieId)

    fun getFavMovie(): DataSource.Factory<Int, MovieEntity> = myMoviesDao.getFavMovie()

    suspend fun insertMovie(movies: List<MovieEntity>) = myMoviesDao.insertMovie(movies)

    suspend fun updateMovie(movie: MovieEntity) = myMoviesDao.updateMovie(movie)

    suspend fun setMovieFavorite(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        myMoviesDao.updateMovie(movie)
    }

    fun getTvShows(): DataSource.Factory<Int, TvShowEntity> = myMoviesDao.getTvShow()

    fun getTopShows(): DataSource.Factory<Int, TvShowEntity> = myMoviesDao.getTopTvShow()

    fun getTvShowsById(tvId: Int): Flow<TvShowEntity> = myMoviesDao.getTvShowById(tvId)

    fun getFavTvShow(): DataSource.Factory<Int, TvShowEntity> = myMoviesDao.getFavTvShow()

    suspend fun insertTvShow(tvShows: List<TvShowEntity>) = myMoviesDao.insertTvShow(tvShows)

    suspend fun updateTvShow(tvShow: TvShowEntity) = myMoviesDao.updateTvShow(tvShow)

    suspend fun setTvShowFavorite(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.isFavorite = newState
        myMoviesDao.updateTvShow(tvShow)
    }
}