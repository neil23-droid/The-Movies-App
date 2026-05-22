package com.example.android.themoviesapp.presentation.mapper

import com.example.android.themoviesapp.Others.extenstions.formatCurrency
import com.example.android.themoviesapp.Others.extenstions.formatReleaseDate
import com.example.android.themoviesapp.Others.extenstions.formatRuntime
import com.example.android.themoviesapp.Others.extenstions.toGenresString
import com.example.android.themoviesapp.Others.extenstions.toRatingFloat
import com.example.android.themoviesapp.domain.model.MovieDetail
import com.example.android.themoviesapp.presentation.models.MovieDetailsUiModel

// presentation/mapper/MovieUiMapper.kt

fun MovieDetail.toMovieDetailUiModel(): MovieDetailsUiModel {
    return MovieDetailsUiModel(
        movieId = this.movieId,
        title = this.title ?: "Unknown Title",
        originalTitle = this.originalTitle?:"Unknown Title",
        overview = this.overview ?: "No overview available",
        posterPath = this.posterPath ?: "",
        backdropPath = this.backdropPath ?: "",
        popularity       = this.popularity,
        originalLanguage = this.originalLanguage ?: "Unknown Language",
        adult            = this.adult,
        releaseDate = this.releaseDate.formatReleaseDate(),
        voteAverage  = this.voteAverage.toRatingFloat(),
        genres = this.genres.toGenresString()
    )
}

