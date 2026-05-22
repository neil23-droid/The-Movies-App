package com.example.android.themoviesapp.presentation.models

import com.example.android.themoviesapp.domain.model.MoviePoster


// presentation/model/MovieDetailUiModel.kt

data class MovieDetailsUiModel(
    val movieId: Long = 0L,
    val title: String = "",              // ← never null, "" if missing
    val originalTitle: String = "",
    val overview: String = "",           // ← never null, "" if missing
    val posterPath: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",        // ← "Mar 15, 2024"
    val popularity: Double = 0.0,
    val genres: String = "",             // ← "Action, Sci-Fi, Drama" ← already joined
    val originalLanguage: String = "",
    val adult: Boolean = false,
    val voteAverage: Float = 0.0f        // ← Float ready for RatingBar
)