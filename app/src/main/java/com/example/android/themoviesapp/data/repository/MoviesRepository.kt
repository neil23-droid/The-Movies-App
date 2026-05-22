package com.example.android.themoviesapp.data.repository

import com.example.android.themoviesapp.data.local.entities.UpcomingMoviesTable
import com.example.android.themoviesapp.domain.model.Movie
import com.example.android.themoviesapp.domain.model.MovieDetail
import com.example.android.themoviesapp.domain.model.MoviePoster
import com.example.android.themoviesapp.domain.model.MovieTrailer
import com.example.android.themoviesapp.domain.result.DomainResult


// Repository interface returns domain models
interface MoviesRepository {
    suspend fun getUpcomingMovies(): DomainResult<List<Movie>>             // domain model
    suspend fun saveUpcomingMovies(movies: List<Movie>): DomainResult<List<Long>>

    suspend fun getMovieDetails(movieId: Long): DomainResult<MovieDetail>

    suspend fun getMoviePosters(movieId: Long): DomainResult<MoviePoster>

    suspend fun getMovieTrailers(movieId: Long): DomainResult<MovieTrailer>
}

