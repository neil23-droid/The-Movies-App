package com.example.android.themoviesapp.data.repository

import com.example.android.themoviesapp.data.local.datasource.MovieLocalDataSource
import com.example.android.themoviesapp.data.local.mapper.toDomain
import com.example.android.themoviesapp.data.local.mapper.toEntity
import com.example.android.themoviesapp.data.local.preferences.AppPreferences
import com.example.android.themoviesapp.data.local.result.LocalResult
import com.example.android.themoviesapp.data.remote.datasource.MovieRemoteDataSource
import com.example.android.themoviesapp.data.remote.handler.ResponseHandler
import com.example.android.themoviesapp.data.remote.mapper.toDomain
import com.example.android.themoviesapp.domain.model.Movie
import com.example.android.themoviesapp.domain.model.MovieDetail
import com.example.android.themoviesapp.domain.model.MoviePoster
import com.example.android.themoviesapp.domain.model.MovieTrailer
import com.example.android.themoviesapp.domain.result.DomainResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Repository implementation — mapping happens here
class MoviesRepositoryImpl(
    private val local: MovieLocalDataSource,
    private val remote: MovieRemoteDataSource,
    private val prefs: AppPreferences,
    private val dispatcher: CoroutineDispatcher
) : MoviesRepository {

    override suspend fun getUpcomingMovies(): DomainResult<List<Movie>> =
        withContext(dispatcher) {
            // Step 1 — check cache validity
            val lastRefresh = prefs.getLastRefreshTime()
            val isCacheValid = lastRefresh != null &&
                    System.currentTimeMillis() - lastRefresh < CACHE_DURATION

            // Step 2 — serve cache if valid
            if (isCacheValid) {
                when (val cached = local.getUpcomingMovies()) {  // ← LocalResult
                    is LocalResult.Success -> {

                        return@withContext DomainResult.Success(            // ← LocalResult → DomainResult
                            cached.data.map { it.toDomain() }
                        )
                    }
                    is LocalResult.Empty   -> Unit  // ← cache empty, fall through to network
                    is LocalResult.Error   -> Unit  // ← cache error, fall through to network
                }
            }

            // Step 3 — fetch from remote, handle all 4 states explicitly
            return@withContext when (val response = remote.getUpcomingMovies()) { // ← ResponseHandler
                is ResponseHandler.Success -> {
                    val movies = response.data.toDomain()
                    local.saveUpcomingMovies(ArrayList(movies.map { it.toEntity() }))
                    val saved = prefs.saveLastRefreshTime(System.currentTimeMillis())
                    if (!saved) {
                        // refresh time not saved — log warning but still return movies
                    }
                    DomainResult.Success(movies)            // ← ResponseHandler → DomainResult
                }

                is ResponseHandler.Error -> {
                    // network failed — try returning stale cache as fallback
                    when (val staleCacheResult = local.getUpcomingMovies()) {
                        is LocalResult.Success -> DomainResult.Success(
                            staleCacheResult.data.map { it.toDomain() }
                        )

                        is LocalResult.Empty ->{
                            DomainResult.Empty
                        }

                        is LocalResult.Error -> DomainResult.Error(                // ← HTTP code dropped here
                            message = staleCacheResult.message,
                            throwable = staleCacheResult.throwable
                        )
                    }
                }

                is ResponseHandler.Loading -> {
                    // remote datasource returned loading
                    // pass it through to let ViewModel handle it
                    DomainResult.Loading
                }

                is ResponseHandler.EmptyResponse -> {
                    // API returned empty body
                    // try returning cache as fallback
                    when (val staleCacheResult = local.getUpcomingMovies()) {
                        is LocalResult.Success -> DomainResult.Success(
                            staleCacheResult.data.map { it.toDomain() }
                        )

                        is LocalResult.Empty ->{
                            DomainResult.Empty
                        }

                        is LocalResult.Error -> DomainResult.Error(                // ← HTTP code dropped here
                            message = staleCacheResult.message,
                            throwable = staleCacheResult.throwable
                        )
                    }
                }
            }

        }


    override suspend fun saveUpcomingMovies(movies: List<Movie>): DomainResult<List<Long>> =
        withContext(dispatcher) {
            val savedResult =  local.saveUpcomingMovies(
                ArrayList(movies.map { it.toEntity() })  // domain model → entity
            )

            return@withContext when (savedResult) {
                is LocalResult.Success -> DomainResult.Success(
                    savedResult.data,
                )

                is LocalResult.Empty ->{
                    DomainResult.Empty
                }

                is LocalResult.Error -> DomainResult.Error(
                    message = savedResult.message,
                    throwable = savedResult.throwable
                )
            }
        }

    override suspend fun getMovieDetails(movieId: Long): DomainResult<MovieDetail> {
        return withContext(dispatcher) {
            when (val response = remote.getMovieDetails(movieId)) {
                is ResponseHandler.Success -> DomainResult.Success(
                    response.data.toDomain()
                )
                is ResponseHandler.Error -> DomainResult.Error(
                    message = response.message,
                    throwable = response.throwable
                )
                is ResponseHandler.EmptyResponse   -> DomainResult.Empty
                is ResponseHandler.Loading -> DomainResult.Loading
            }
        }
    }

    override suspend fun getMoviePosters(movieId: Long): DomainResult<MoviePoster> {
        return withContext(dispatcher) {
            when (val response = remote.getMovieImages(movieId)) {
                is ResponseHandler.Success -> DomainResult.Success(
                    response.data.toDomain()
                )
                is ResponseHandler.Error -> DomainResult.Error(
                    message = response.message,
                    throwable = response.throwable
                )
                is ResponseHandler.EmptyResponse   -> DomainResult.Empty
                is ResponseHandler.Loading -> DomainResult.Loading
            }
        }
    }

    override suspend fun getMovieTrailers(movieId: Long): DomainResult<MovieTrailer> {
        return withContext(dispatcher) {
            when (val response = remote.getMovieTrailerUrl(movieId)) {
                is ResponseHandler.Success -> DomainResult.Success(
                    response.data.toDomain()
                )
                is ResponseHandler.Error -> DomainResult.Error(
                    message = response.message,
                    throwable = response.throwable
                )
                is ResponseHandler.EmptyResponse   -> DomainResult.Empty
                is ResponseHandler.Loading -> DomainResult.Loading
            }
        }
    }


    companion object {
        private const val CACHE_DURATION = 30 * 60 * 1000L  // 30 minutes
    }
}