package com.slv.mysubmissionandroidexpert.core.data.source

import androidx.lifecycle.asFlow
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.slv.mysubmissionandroidexpert.core.data.source.local.LocalDataSource
import com.slv.mysubmissionandroidexpert.core.data.source.remote.RemoteDataSource
import com.slv.mysubmissionandroidexpert.core.data.source.remote.network.ApiResponse
import com.slv.mysubmissionandroidexpert.core.data.source.remote.reponse.DetailResponse
import com.slv.mysubmissionandroidexpert.core.data.source.remote.reponse.ResultsItem
import com.slv.mysubmissionandroidexpert.core.domain.model.Movie
import com.slv.mysubmissionandroidexpert.core.domain.model.TvShow
import com.slv.mysubmissionandroidexpert.core.domain.repository.IMyMoviesRepository
import com.slv.mysubmissionandroidexpert.core.utils.DataMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyMoviesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMyMoviesRepository {

    override fun getPopularMovie(): Flow<Resource<PagedList<Movie>>> =
        object : NetworkBoundResource<PagedList<Movie>, List<ResultsItem>>() {
            override fun loadFromDB(): Flow<PagedList<Movie>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(
                    localDataSource.getMovies().map { DataMapper.mapEntitiesToDomain(it) }, config
                )
                    .build()
                    .asFlow()
            }

            override fun shouldFetch(data: PagedList<Movie>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getPopularMovie()

            override suspend fun saveCallResult(data: List<ResultsItem>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }

        }.asFlow()

    override fun getTopMovie(): Flow<Resource<PagedList<Movie>>> =
        object : NetworkBoundResource<PagedList<Movie>, List<ResultsItem>>() {
            override fun loadFromDB(): Flow<PagedList<Movie>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(
                    localDataSource.getTopMovies().map { DataMapper.mapEntitiesToDomain(it) },
                    config
                )
                    .build()
                    .asFlow()
            }

            override fun shouldFetch(data: PagedList<Movie>?): Boolean =
                data == null || data.isEmpty()


            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getPopularMovie()

            override suspend fun saveCallResult(data: List<ResultsItem>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }

        }.asFlow()

    override fun getDetailMovie(movieId: Int): Flow<Resource<Movie>> =
        object : NetworkBoundResource<Movie, DetailResponse>() {
            override fun loadFromDB(): Flow<Movie> {
                return localDataSource.getMovieById(movieId).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: Movie?): Boolean =
                data?.rate == null || data.genre == null

            override suspend fun createCall(): Flow<ApiResponse<DetailResponse>> =
                remoteDataSource.getDetailMovie(movieId)

            override suspend fun saveCallResult(data: DetailResponse) {
                val dataMovie = DataMapper.mapDetailResponseToEntity(data)
                localDataSource.updateMovie(dataMovie)
            }

        }.asFlow()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val dataMovie = DataMapper.mapDomainToEntity(movie)
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.setMovieFavorite(dataMovie, state)
        }
    }

    override fun getFavoriteMovie(): Flow<PagedList<Movie>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(
            localDataSource.getFavMovie().map { DataMapper.mapEntitiesToDomain(it) }, config
        )
            .build()
            .asFlow()
    }

    override fun getPopularTvShow(): Flow<Resource<PagedList<TvShow>>> =
        object : NetworkBoundResource<PagedList<TvShow>, List<ResultsItem>>() {
            override fun loadFromDB(): Flow<PagedList<TvShow>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(
                    localDataSource.getTvShows().map { DataMapper.mapTvEntitiesToDomain(it) },
                    config
                )
                    .build()
                    .asFlow()
            }

            override fun shouldFetch(data: PagedList<TvShow>?): Boolean =
                data == null || data.isEmpty()


            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getPopularTv()

            override suspend fun saveCallResult(data: List<ResultsItem>) {
                val tvList = DataMapper.mapTvResponsesToEntities(data)
                localDataSource.insertTvShow(tvList)
            }

        }.asFlow()

    override fun getTopTvShow(): Flow<Resource<PagedList<TvShow>>> =
        object : NetworkBoundResource<PagedList<TvShow>, List<ResultsItem>>() {
            override fun loadFromDB(): Flow<PagedList<TvShow>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(
                    localDataSource.getTopShows().map { DataMapper.mapTvEntitiesToDomain(it) },
                    config
                )
                    .build()
                    .asFlow()
            }

            override fun shouldFetch(data: PagedList<TvShow>?): Boolean =
                data == null || data.isEmpty()


            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getPopularTv()

            override suspend fun saveCallResult(data: List<ResultsItem>) {
                val tvList = DataMapper.mapTvResponsesToEntities(data)
                localDataSource.insertTvShow(tvList)
            }

        }.asFlow()

    override fun getDetailTvShow(tvId: Int): Flow<Resource<TvShow>> =
        object : NetworkBoundResource<TvShow, DetailResponse>() {
            override fun loadFromDB(): Flow<TvShow> {
                return localDataSource.getTvShowsById(tvId).map {
                    DataMapper.mapTvEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: TvShow?): Boolean =
                data?.rate == null || data.genre == null


            override suspend fun createCall(): Flow<ApiResponse<DetailResponse>> =
                remoteDataSource.getDetailTvShow(tvId)

            override suspend fun saveCallResult(data: DetailResponse) {
                val dataTv = DataMapper.mapTvDetailResponseToEntity(data)
                localDataSource.updateTvShow(dataTv)
            }

        }.asFlow()

    override fun setFavoriteTv(tvShow: TvShow, state: Boolean) {
        val dataTvShow = DataMapper.mapTvDomainToEntity(tvShow)
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.setTvShowFavorite(dataTvShow, state)
        }
    }

    override fun getFavoriteTv(): Flow<PagedList<TvShow>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(
            localDataSource.getFavTvShow().map { DataMapper.mapTvEntitiesToDomain(it) }, config
        )
            .build()
            .asFlow()
    }


}