package com.example.android.themoviesapp.data.remote.datasource

import com.example.android.themoviesapp.data.remote.api.MoviesRemoteApiInterface
import com.example.android.themoviesapp.data.remote.handler.NetworkHandler
import com.example.android.themoviesapp.data.remote.handler.ResponseHandler
import com.example.android.themoviesapp.data.remote.models.response.GetMovieDetailsResponse
import com.example.android.themoviesapp.data.remote.models.response.GetMoviePostersResponse
import com.example.android.themoviesapp.data.remote.models.response.GetMovieTrailerUrlsResponse
import com.example.android.themoviesapp.data.remote.models.response.GetUpComingMoviesResponse

// data/remote/datasource/MovieRemoteDataSourceImpl.kt
class MovieRemoteDataSourceImpl(
    private val api: MoviesRemoteApiInterface,    // depends on API interface
    private val networkHandler: NetworkHandler    // depends on interface
) : MovieRemoteDataSource {

    override suspend fun getUpcomingMovies(): ResponseHandler<GetUpComingMoviesResponse> {
        return networkHandler.handleApiCall { api.getUpComingMovies() }
    }

    override suspend fun getMovieDetails(
        movieId: Long
    ): ResponseHandler<GetMovieDetailsResponse> {
        return networkHandler.handleApiCall { api.getMovieDetails(movieId) }
    }

    override suspend fun getMovieImages(
        movieId: Long
    ): ResponseHandler<GetMoviePostersResponse> {
        return networkHandler.handleApiCall { api.getMovieImages(movieId) }
    }

    override suspend fun getMovieTrailerUrl(
        movieId: Long
    ): ResponseHandler<GetMovieTrailerUrlsResponse> {
        return networkHandler.handleApiCall { api.getMovieTrailerUrl(movieId) }
    }
}