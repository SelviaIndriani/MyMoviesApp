package com.slv.mysubmissionandroidexpert.core.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.slv.mysubmissionandroidexpert.core.data.source.local.entity.MovieEntity
import com.slv.mysubmissionandroidexpert.core.data.source.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyMoviesDao {

    @Query("SELECT * FROM movie_entities")
    fun getMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movie_entities order by rate desc")
    fun getTopMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movie_entities WHERE movieId = :movieId")
    fun getMovieById(movieId: Int): Flow<MovieEntity>

    @Query("SELECT * FROM movie_entities WHERE isFavorite = 1")
    fun getFavMovie(): DataSource.Factory<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<MovieEntity>)

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM tv_show_entities")
    fun getTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM tv_show_entities order by rate desc")
    fun getTopTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM tv_show_entities WHERE tvId = :tvId")
    fun getTvShowById(tvId: Int): Flow<TvShowEntity>

    @Query("SELECT * FROM tv_show_entities WHERE isFavorite = 1")
    fun getFavTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(tvShows: List<TvShowEntity>)

    @Update
    suspend fun updateTvShow(tvShows: TvShowEntity)
}