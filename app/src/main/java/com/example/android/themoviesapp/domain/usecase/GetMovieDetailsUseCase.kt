package com.example.android.themoviesapp.domain.usecase

import com.example.android.themoviesapp.data.repository.MoviesRepository
import com.example.android.themoviesapp.di.IoDispatcher
import com.example.android.themoviesapp.domain.model.MovieDetail
import com.example.android.themoviesapp.domain.model.MoviePoster
import com.example.android.themoviesapp.domain.model.MovieTrailer
import com.example.android.themoviesapp.domain.result.DomainResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

// domain/usecase/GetMovieDetailUseCase.kt

class GetMovieDetailUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun getMovieDetail(
        movieId: Long
    ): DomainResult<MovieDetail> = withContext(dispatcher) {

        val movieDetailDeferred = async { moviesRepository.getMovieDetails(movieId) }

        val movieDetailResult = movieDetailDeferred.await()

        when (movieDetailResult) {
            is DomainResult.Success -> {
                DomainResult.Success(
                    movieDetailResult.data
                )
            }

            is DomainResult.Error -> DomainResult.Error(
                message = movieDetailResult.message,
                throwable = movieDetailResult.throwable
            )

            is DomainResult.Empty -> DomainResult.Empty
            is DomainResult.Loading -> DomainResult.Loading
        }
    }

    suspend fun getMoviePosters(
        movieId: Long
    ): DomainResult<MoviePoster> = withContext(dispatcher) {
        return@withContext when (val result = moviesRepository.getMoviePosters(movieId)) {
            is DomainResult.Success -> DomainResult.Success(result.data)
            is DomainResult.Error -> DomainResult.Error(
                message = result.message,
                throwable = result.throwable
            )

            is DomainResult.Empty -> DomainResult.Empty
            is DomainResult.Loading -> DomainResult.Loading
        }
    }

    suspend fun getMovieTrailers(movieId: Long):DomainResult<MovieTrailer> = withContext(dispatcher){
        return@withContext when (val result = moviesRepository.getMovieTrailers(movieId)) {
            is DomainResult.Success -> DomainResult.Success(result.data)
            is DomainResult.Error -> DomainResult.Error(
                message = result.message,
                throwable = result.throwable
            )

            is DomainResult.Empty -> DomainResult.Empty
            is DomainResult.Loading -> DomainResult.Loading
        }
    }
}