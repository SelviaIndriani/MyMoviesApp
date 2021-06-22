package com.slv.mysubmissionandroidexpert.core.di

import com.slv.mysubmissionandroidexpert.core.data.source.MyMoviesRepository
import com.slv.mysubmissionandroidexpert.core.domain.repository.IMyMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(includes = [RetrofitModule::class, RoomModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(myMovieRepository: MyMoviesRepository): IMyMoviesRepository
}