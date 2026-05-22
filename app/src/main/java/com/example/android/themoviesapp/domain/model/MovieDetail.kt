package com.example.android.themoviesapp.domain.model

// domain/model/MovieDetail.kt

data class MovieDetail(
    val movieId: Long,
    val title: String?,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Long,
    val popularity: Double,
    val originalLanguage: String?,
    val originalTitle: String?,
    val adult: Boolean,
    val video: Boolean,
    val budget: Long,
    val revenue: Long,
    val runtime: Long,
    val status: String?,
    val tagline: String?,
    val homepage: String?,
    val imdbId: String?,
    val genres: List<MovieGenre>    // ← uses MovieGenre domain model
)
