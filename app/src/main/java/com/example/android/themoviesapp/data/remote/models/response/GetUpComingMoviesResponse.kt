package com.example.android.themoviesapp.data.remote.models.response

import com.google.gson.annotations.SerializedName

// data/remote/model/response/GetUpComingMoviesResponse.kt

data class GetUpComingMoviesResponse(
    @SerializedName("dates") var date: Dates? = null,
    @SerializedName("page") var page: Int = 0,
    @SerializedName("results") var moviesList: ArrayList<UpComingMovies> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int = 0,
    @SerializedName("total_results") var totalResults: Int = 0,
)

data class Dates(
    @SerializedName("maximum") var maximum: String? = null,
    @SerializedName("minimum") var minimum: String? = null
)

data class UpComingMovies(
    @SerializedName("adult") var adult: Boolean = false,
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("genre_ids") var genreIds: ArrayList<Long> = arrayListOf(),
    @SerializedName("id") var movieId: Long = 0,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("popularity") var popularity: Double = 0.0,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("video") var video: Boolean = false,
    @SerializedName("vote_average") var voteAverage: Double = 0.0,
    @SerializedName("vote_count") var voteCount: Long = 0
)
