package com.slv.favorite.di

import android.content.Context
import com.slv.favorite.movie.FavoriteMovieFragment
import com.slv.favorite.tvshow.FavoriteTvShowFragment
import com.slv.mysubmissionandroidexpert.presentation.di.FavoriteDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteDependencies::class])
interface FavoriteComponent {

    fun inject(favoriteMovieFragment: FavoriteMovieFragment)
    fun inject(favoriteTvShowFragment: FavoriteTvShowFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun favoriteDependencies(favoriteDependencies: FavoriteDependencies): Builder
        fun build(): FavoriteComponent
    }
}