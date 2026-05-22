package com.example.android.themoviesapp.domain.usecase

import com.example.android.themoviesapp.data.repository.MoviesRepository
import com.example.android.themoviesapp.domain.model.Movie
import com.example.android.themoviesapp.domain.result.DomainResult
import javax.inject.Inject

// domain/usecase/GetUpcomingMoviesUseCase.kt

class DisplayUpcomingMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(): DomainResult<List<Movie>> {
        return moviesRepository.getUpcomingMovies()
    }
}