package com.example.android.themoviesapp.data.remote.models.response

import com.google.gson.annotations.SerializedName

// data/remote/model/response/GetMovieDetailsResponse.kt

data class GetMovieDetailsResponse(
    @SerializedName("adult") var adult: Boolean = false,
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("budget") var budget: Long = 0,
    @SerializedName("genres") var genres: ArrayList<Genre> = arrayListOf(),
    @SerializedName("homepage") var homepage: String? = null,
    @SerializedName("id") var id: Long = 0,
    @SerializedName("imdb_id") var imdbId: String? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("popularity") var popularity: Double = 0.0,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("revenue") var revenue: Long = 0,
    @SerializedName("runtime") var runtime: Long = 0,
    @SerializedName("status") var status: String? = null,
    @SerializedName("tagline") var tagline: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("video") var video: Boolean = false,
    @SerializedName("vote_average") var voteAverage: Double = 0.0,
    @SerializedName("vote_count") var voteCount: Long = 0
)

data class Genre(
    @SerializedName("id") var id: Long = 0,
    @SerializedName("name") var name: String? = null
)