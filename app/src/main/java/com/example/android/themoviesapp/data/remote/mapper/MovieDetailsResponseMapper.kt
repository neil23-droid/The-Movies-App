package com.example.android.themoviesapp.data.remote.mapper

import com.example.android.themoviesapp.data.remote.models.response.Genre
import com.example.android.themoviesapp.data.remote.models.response.GetMovieDetailsResponse
import com.example.android.themoviesapp.domain.model.MovieDetail
import com.example.android.themoviesapp.domain.model.MovieGenre

// data/remote/mapper/MovieDetailsResponseMapper.kt

fun Genre.toDomain(): MovieGenre {
    return MovieGenre(
        id = this.id,
        name = this.name
    )
}

fun GetMovieDetailsResponse.toDomain(): MovieDetail {
    return MovieDetail(
        movieId = this.id,
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
        adult = this.adult,
        video = this.video,
        budget = this.budget,
        revenue = this.revenue,
        runtime = this.runtime,
        status = this.status,
        tagline = this.tagline,
        homepage = this.homepage,
        imdbId = this.imdbId,
        genres = this.genres.map { it.toDomain() }  // ← Genre.toDomain() above
    )
}