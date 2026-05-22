package com.example.android.themoviesapp.di

import com.example.android.themoviesapp.data.local.dao.BookedTicketHistoryDao
import com.example.android.themoviesapp.data.local.dao.MoviesDao
import com.example.android.themoviesapp.data.local.datasource.BookedTicketLocalDataSource
import com.example.android.themoviesapp.data.local.datasource.BookedTicketLocalDataSourceImpl
import com.example.android.themoviesapp.data.local.datasource.MovieLocalDataSource
import com.example.android.themoviesapp.data.local.datasource.MovieLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    // DataSource receives only its DAO
    @Provides
    @Singleton
    fun provideMovieLocalDataSource(
        moviesDao: MoviesDao                        // only MoviesDao
    ): MovieLocalDataSource = MovieLocalDataSourceImpl(moviesDao)

    @Provides
    @Singleton
    fun provideBookedTicketLocalDataSource(
        bookedTicketHistoryDao: BookedTicketHistoryDao  // only BookedTicketHistoryDao
    ): BookedTicketLocalDataSource = BookedTicketLocalDataSourceImpl(bookedTicketHistoryDao)
}
