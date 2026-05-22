package com.example.android.themoviesapp.data.remote.api

// data/remote/api/MoviesRemoteApiInterface.kt

import com.example.android.themoviesapp.Others.MOVIES_API_KEY
import com.example.android.themoviesapp.data.remote.models.response.GetMovieDetailsResponse
import com.example.android.themoviesapp.data.remote.models.response.GetMoviePostersResponse
import com.example.android.themoviesapp.data.remote.models.response.GetMovieTrailerUrlsResponse
import com.example.android.themoviesapp.data.remote.models.response.GetUpComingMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesRemoteApiInterface {

    @GET("movie/upcoming?api_key=${MOVIES_API_KEY}")
    suspend fun getUpComingMovies(): Response<GetUpComingMoviesResponse>

    @GET("movie/{movieId}?api_key=${MOVIES_API_KEY}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Long
    ): Response<GetMovieDetailsResponse>

    @GET("movie/{movieId}/images?api_key=${MOVIES_API_KEY}")
    suspend fun getMovieImages(
        @Path("movieId") movieId: Long
    ): Response<GetMoviePostersResponse>

    @GET("movie/{movieId}/videos?api_key=${MOVIES_API_KEY}")
    suspend fun getMovieTrailerUrl(
        @Path("movieId") movieId: Long
    ): Response<GetMovieTrailerUrlsResponse>
}