package com.example.android.themoviesapp.presentation.mapper

import com.example.android.themoviesapp.Others.extenstions.formatReleaseDate
import com.example.android.themoviesapp.Others.extenstions.toPopularityLabel
import com.example.android.themoviesapp.domain.model.Movie
import com.example.android.themoviesapp.presentation.models.MoviesListUiModel

// presentation/mapper/MovieUiMapper.kt
fun Movie.toMoviesListUiModel(): MoviesListUiModel {
    return MoviesListUiModel(
        movieId = this.movieId,

        // null safety with fallbacks
        title = this.title ?: "Unknown Title",
        overview = this.overview ?: "No overview available",
        posterPath = this.posterPath ?: "",
        backdropPath = this.backdropPath ?: "",
        originalLanguage = this.originalLanguage?.uppercase() ?: "N/A",
        originalTitle = this.originalTitle ?: "Unknown Title",

        // formatted values
        releaseDate = this.releaseDate.formatReleaseDate(),
        popularity = this.popularity.toPopularityLabel(),

        // UI flags
        isAdultContentVisible = this.adult,
        hasVideo = this.video
    )
}