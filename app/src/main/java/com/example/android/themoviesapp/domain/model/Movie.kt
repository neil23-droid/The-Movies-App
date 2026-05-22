package com.example.android.themoviesapp.domain.model

// domain/model/Movie.kt
// Clean — no Room annotations, no framework dependency
data class Movie(
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
    val genreIds: ArrayList<Long>,
    val adult: Boolean,
    val video: Boolean
)