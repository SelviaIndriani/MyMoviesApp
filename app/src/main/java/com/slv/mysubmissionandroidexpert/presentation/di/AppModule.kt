package com.slv.mysubmissionandroidexpert.presentation.di

import com.slv.mysubmissionandroidexpert.core.data.source.MyMoviesRepository
import com.slv.mysubmissionandroidexpert.core.domain.usecase.MyMoviesInteractor
import com.slv.mysubmissionandroidexpert.core.domain.usecase.MyMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMyMoviesUseCase(myMoviesRepository: MyMoviesRepository): MyMoviesUseCase =
        MyMoviesInteractor(myMoviesRepository)
}