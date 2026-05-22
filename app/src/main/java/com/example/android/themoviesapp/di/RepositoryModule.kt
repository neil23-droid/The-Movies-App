package com.example.android.themoviesapp.di

import com.example.android.themoviesapp.data.local.datasource.BookedTicketLocalDataSource
import com.example.android.themoviesapp.data.local.datasource.MovieLocalDataSource
import com.example.android.themoviesapp.data.local.preferences.AppPreferences
import com.example.android.themoviesapp.data.remote.datasource.MovieRemoteDataSource
import com.example.android.themoviesapp.data.repository.BookedTicketRepository
import com.example.android.themoviesapp.data.repository.BookedTicketRepositoryImpl
import com.example.android.themoviesapp.data.repository.MoviesRepository
import com.example.android.themoviesapp.data.repository.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(
        local: MovieLocalDataSource,
        remote: MovieRemoteDataSource,
        prefs: AppPreferences,
        @DefaultDispatcher dispatcher: CoroutineDispatcher  // ← injected
    ): MoviesRepository = MoviesRepositoryImpl(local, remote, prefs, dispatcher)

    @Provides
    @Singleton
    fun provideBookedTicketRepository(
        local: BookedTicketLocalDataSource,  // Hilt injects interface
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): BookedTicketRepository = BookedTicketRepositoryImpl(local)
}
