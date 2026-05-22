package com.example.android.themoviesapp.data.remote.mapper

import com.example.android.themoviesapp.data.remote.models.response.GetMoviePostersResponse
import com.example.android.themoviesapp.data.remote.models.response.Poster
import com.example.android.themoviesapp.domain.model.MoviePoster

// data/remote/mapper/MoviePostersResponseMapper.kt

fun Poster.toDomain(): String {
    return this.filePath ?: ""
}

fun GetMoviePostersResponse.toDomain(): MoviePoster {
    return MoviePoster(
        movieId = this.movieId,
        filePaths = this.posters.map { it.toDomain() }  // ← Poster.toDomain() above
    )
}
