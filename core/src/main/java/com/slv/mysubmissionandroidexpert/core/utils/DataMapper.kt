package com.slv.mysubmissionandroidexpert.core.utils

import com.slv.mysubmissionandroidexpert.core.data.source.local.entity.MovieEntity
import com.slv.mysubmissionandroidexpert.core.data.source.local.entity.TvShowEntity
import com.slv.mysubmissionandroidexpert.core.data.source.remote.reponse.DetailResponse
import com.slv.mysubmissionandroidexpert.core.data.source.remote.reponse.ResultsItem
import com.slv.mysubmissionandroidexpert.core.domain.model.Movie
import com.slv.mysubmissionandroidexpert.core.domain.model.TvShow

object DataMapper {

    fun mapResponsesToEntities(input: List<ResultsItem>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                movieId = it.id,
                title = it.title,
                rate = it.voteAverage,
                release_date = it.releaseDate,
                overview = it.overview,
                poster = it.posterPath
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapTvResponsesToEntities(input: List<ResultsItem>): List<TvShowEntity> {
        val tvList = ArrayList<TvShowEntity>()
        input.map {
            val tv = TvShowEntity(
                tvId = it.id,
                title = it.name,
                rate = it.voteAverage,
                release_date = it.firstAirDate,
                overview = it.overview,
                poster = it.posterPath
            )
            tvList.add(tv)
        }
        return tvList
    }

    fun mapEntitiesToDomain(input: MovieEntity): Movie =
        Movie(
            movieId = input.movieId,
            title = input.title,
            rate = input.rate,
            release_date = input.release_date,
            overview = input.overview,
            status = input.status,
            poster = input.poster,
            duration = input.duration,
            genre = input.genre,
            isFavorite = input.isFavorite
        )

    fun mapTvEntitiesToDomain(input: TvShowEntity): TvShow =
        TvShow(
            tvId = input.tvId,
            title = input.title,
            rate = input.rate,
            release_date = input.release_date,
            status = input.status,
            overview = input.overview,
            poster = input.poster,
            duration = input.duration,
            genre = input.genre,
            isFavorite = input.isFavorite
        )

    fun mapDetailResponseToEntity(input: DetailResponse): MovieEntity =
        MovieEntity(
            movieId = input.id,
            title = input.title,
            release_date = input.releaseDate,
            genre = input.genres.joinToString { it.name },
            duration = input.runtime,
            rate = input.voteAverage,
            overview = input.overview,
            status = input.status,
            poster = input.posterPath,
            isFavorite = false
        )

    fun mapTvDetailResponseToEntity(input: DetailResponse): TvShowEntity =
        TvShowEntity(
            tvId = input.id,
            title = input.name,
            release_date = input.firstAirDate,
            genre = input.genres.joinToString { it.name },
            duration = input.episodeRunTime[0],
            rate = input.voteAverage,
            overview = input.overview,
            status = input.status,
            poster = input.posterPath,
            isFavorite = false
        )

    fun mapDomainToEntity(input: Movie) = MovieEntity(
        movieId = input.movieId,
        title = input.title,
        rate = input.rate,
        release_date = input.release_date,
        overview = input.overview,
        status = input.status,
        poster = input.poster,
        duration = input.duration,
        genre = input.genre,
        isFavorite = input.isFavorite
    )

    fun mapTvDomainToEntity(input: TvShow) = TvShowEntity(
        tvId = input.tvId,
        title = input.title,
        rate = input.rate,
        release_date = input.release_date,
        overview = input.overview,
        status = input.status,
        poster = input.poster,
        duration = input.duration,
        genre = input.genre,
        isFavorite = input.isFavorite
    )
}