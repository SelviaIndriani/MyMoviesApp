package com.slv.mysubmissionandroidexpert.core.data.source.remote.reponse

import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("original_title")
    val originalTitle: String,

    @field:SerializedName("release_date")
    val releaseDate: String,

    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("episode_run_time")
    val episodeRunTime: List<Int>,

    @field:SerializedName("genres")
    val genres: List<GenresItem>,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("runtime")
    val runtime: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("status")
    val status: String
)

data class GenresItem(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)