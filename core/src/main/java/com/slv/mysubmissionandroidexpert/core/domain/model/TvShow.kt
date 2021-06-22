package com.slv.mysubmissionandroidexpert.core.domain.model

data class TvShow(
    var tvId: Int,
    var title: String? = null,
    var rate: Double? = 0.0,
    var release_date: String? = null,
    var genre: String? = null,
    var duration: Int? = 0,
    var overview: String? = null,
    var poster: String? = null,
    var status: String? = null,
    var isFavorite: Boolean = false
)