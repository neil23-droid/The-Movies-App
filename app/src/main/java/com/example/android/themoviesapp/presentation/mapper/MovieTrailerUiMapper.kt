package com.example.android.themoviesapp.presentation.mapper

import com.example.android.themoviesapp.domain.model.MovieTrailer
import com.example.android.themoviesapp.presentation.models.MoviePostersUiModel
import com.example.android.themoviesapp.presentation.models.MovieTrailerUiModel


fun MovieTrailer.toMovieTrailerUiModel(): List<MovieTrailerUiModel> {
    return this.trailers.map { trailerInfo ->
        MovieTrailerUiModel(
            movieId = this.movieId,
            key = trailerInfo.key ?: "",
            name = trailerInfo.name ?: "Unknown Trailer",
            site = trailerInfo.site ?: "Unknown Site",
            type = trailerInfo.type ?: "Unknown Type"
        )
    }
}