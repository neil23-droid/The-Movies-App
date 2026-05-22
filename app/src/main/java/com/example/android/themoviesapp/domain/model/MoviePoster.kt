package com.example.android.themoviesapp.domain.model

// domain/model/MoviePoster.kt

data class MoviePoster(
    val movieId: Long,
    val filePaths: List<String>     // ← flattened from ArrayList<Poster> to just paths
)
