package com.example.android.themoviesapp.presentation.models

// presentation/model/MovieUiModel.kt
data class MoviesListUiModel(
    val movieId: Long,

    // display ready strings — never null, always has fallback
    val title: String="",
    val overview: String="",
    val posterPath: String="",
    val backdropPath: String="",
    val releaseDate: String="",        // "March 15, 2024"  ← formatted
    val originalLanguage: String="",
    val originalTitle: String="",
    val voteAverage: String="",        // "7.5 ⭐"          ← formatted
    val voteCount: String="",          // "1,234 votes"     ← formatted
    val popularity: String="",         // "Trending 🔥" or "Popular" ← formatted

    // UI flags
    val isAdultContentVisible: Boolean=false,  // drives a badge visibility
    val hasVideo: Boolean=false                // drives a play button visibility
)