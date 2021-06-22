package com.slv.mysubmissionandroidexpert.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.slv.mysubmissionandroidexpert.core.data.source.local.entity.MovieEntity
import com.slv.mysubmissionandroidexpert.core.data.source.local.entity.TvShowEntity
import javax.inject.Singleton

@Database(entities = [MovieEntity::class, TvShowEntity::class], version = 2, exportSchema = false)
@Singleton
abstract class MyMoviesDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME: String = "MyMovie.db"
    }

    abstract fun MyMoviesDao(): MyMoviesDao
}