package com.example.android.themoviesapp.domain.model

// domain/model/MovieTrailer.kt

data class MovieTrailer(
    val movieId: Long,
    val trailers: List<TrailerInfo>     // ← uses TrailerInfo domain model
)
