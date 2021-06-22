package com.slv.mysubmissionandroidexpert.core.data.source.remote.network


import com.slv.mysubmissionandroidexpert.core.BuildConfig
import com.slv.mysubmissionandroidexpert.core.data.source.remote.reponse.DetailResponse
import com.slv.mysubmissionandroidexpert.core.data.source.remote.reponse.MyMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MyMovieResponse

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): DetailResponse

    @GET("tv/popular")
    suspend fun getPopularTv(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MyMovieResponse

    @GET("tv/{tv_id}")
    suspend fun getDetailTvShow(
        @Path("tv_id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): DetailResponse
}