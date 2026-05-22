package com.example.android.themoviesapp.di

import android.content.Context
import com.example.android.themoviesapp.data.local.dao.BookedTicketHistoryDao
import com.example.android.themoviesapp.data.local.dao.MoviesDao
import com.example.android.themoviesapp.data.local.database.MoviesAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MoviesAppDatabase = MoviesAppDatabase.getInstance(context)

    // Database provides individual DAOs
    @Provides
    @Singleton
    fun provideMoviesDao(db: MoviesAppDatabase): MoviesDao = db.moviesDao

    @Provides
    @Singleton
    fun provideBookedTicketDao(
        db: MoviesAppDatabase
    ): BookedTicketHistoryDao = db.bookedTicketHistoryDao
}
