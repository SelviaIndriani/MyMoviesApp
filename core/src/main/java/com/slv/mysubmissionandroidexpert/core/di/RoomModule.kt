package com.slv.mysubmissionandroidexpert.core.di

import android.content.Context
import androidx.room.Room
import com.slv.mysubmissionandroidexpert.core.BuildConfig
import com.slv.mysubmissionandroidexpert.core.data.source.local.room.MyMoviesDao
import com.slv.mysubmissionandroidexpert.core.data.source.local.room.MyMoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideMyMovieDb(@ApplicationContext context: Context): MyMoviesDatabase {

        val passphrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.PASSPHRASE.toCharArray())
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(
            context,
            MyMoviesDatabase::class.java,
            MyMoviesDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Singleton
    @Provides
    fun provideMyMovieDao(database: MyMoviesDatabase): MyMoviesDao = database.MyMoviesDao()
}