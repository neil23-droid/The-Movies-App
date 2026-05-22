package com.example.android.themoviesapp.data.remote.mapper

import com.example.android.themoviesapp.data.remote.models.response.GetMovieTrailerUrlsResponse
import com.example.android.themoviesapp.data.remote.models.response.UrlResult
import com.example.android.themoviesapp.domain.model.MovieTrailer
import com.example.android.themoviesapp.domain.model.TrailerInfo

// data/remote/mapper/MovieTrailerUrlsResponseMapper.kt

fun UrlResult.toDomain(): TrailerInfo {
    return TrailerInfo(
        key = this.key,
        site = this.site,
        type = this.type,
        name = this.name,
        isOfficial = this.official
    )
}

fun GetMovieTrailerUrlsResponse.toDomain(): MovieTrailer {
    return MovieTrailer(
        movieId = this.id,
        trailers = this.urlResults
           /* .filter { it.site == "YouTube" }        // ← only YouTube trailers
            .filter { it.type == "Trailer" }        // ← only actual trailers, not teasers*/
            .map { it.toDomain() }                  // ← UrlResult.toDomain() above
    )
}