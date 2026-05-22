package com.example.android.themoviesapp.presentation.models

data class MoviePostersUiModel(
    val movieId: Long = 0L,
    val filePath:String = ""  // ← flattened from ArrayList<Poster> to just paths
)