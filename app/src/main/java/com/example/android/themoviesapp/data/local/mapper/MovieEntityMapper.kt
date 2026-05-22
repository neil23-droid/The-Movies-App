package com.example.android.themoviesapp.data.local.mapper

import com.example.android.themoviesapp.data.local.entities.UpcomingMoviesTable
import com.example.android.themoviesapp.domain.model.Movie

// data/local/mapper/MovieEntityMapper.kt
fun UpcomingMoviesTable.toDomain(): Movie {
    return Movie(
        movieId = this.movieId,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        popularity = this.popularity,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        genreIds = this.genreIds,
        adult = this.adult,
        video = this.video
    )
}

fun Movie.toEntity(): UpcomingMoviesTable {
    return UpcomingMoviesTable(
        movieId = this.movieId,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        popularity = this.popularity,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        genreIds = this.genreIds,
        adult = this.adult,
        video = this.video
    )
}