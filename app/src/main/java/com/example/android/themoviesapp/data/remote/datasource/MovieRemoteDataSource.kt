package com.example.android.themoviesapp.data.remote.datasource

import com.example.android.themoviesapp.data.remote.handler.ResponseHandler
import com.example.android.themoviesapp.data.remote.models.response.GetMovieDetailsResponse
import com.example.android.themoviesapp.data.remote.models.response.GetMoviePostersResponse
import com.example.android.themoviesapp.data.remote.models.response.GetMovieTrailerUrlsResponse
import com.example.android.themoviesapp.data.remote.models.response.GetUpComingMoviesResponse

// data/remote/datasource/MovieRemoteDataSource.kt
interface MovieRemoteDataSource {
    suspend fun getUpcomingMovies(): ResponseHandler<GetUpComingMoviesResponse>
    suspend fun getMovieDetails(movieId: Long): ResponseHandler<GetMovieDetailsResponse>
    suspend fun getMovieImages(movieId: Long): ResponseHandler<GetMoviePostersResponse>
    suspend fun getMovieTrailerUrl(movieId: Long): ResponseHandler<GetMovieTrailerUrlsResponse>
}