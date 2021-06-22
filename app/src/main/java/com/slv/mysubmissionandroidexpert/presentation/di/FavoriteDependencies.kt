package com.slv.mysubmissionandroidexpert.presentation.di

import com.slv.mysubmissionandroidexpert.core.domain.usecase.MyMoviesUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteDependencies {

    fun myMovieUseCase(): MyMoviesUseCase
}