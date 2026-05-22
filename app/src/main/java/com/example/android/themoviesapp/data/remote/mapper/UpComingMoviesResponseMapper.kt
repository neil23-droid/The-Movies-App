package com.example.android.themoviesapp.data.remote.mapper

import com.example.android.themoviesapp.data.remote.models.response.GetUpComingMoviesResponse
import com.example.android.themoviesapp.data.remote.models.response.UpComingMovies
import com.example.android.themoviesapp.domain.model.Movie

// data/remote/mapper/UpComingMoviesResponseMapper.kt

fun UpComingMovies.toDomain(): Movie {
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

fun GetUpComingMoviesResponse.toDomain(): List<Movie> {
    return this.moviesList.map { it.toDomain() }
}