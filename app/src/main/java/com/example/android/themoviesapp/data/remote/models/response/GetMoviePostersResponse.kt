package com.example.android.themoviesapp.data.remote.models.response

import com.google.gson.annotations.SerializedName

// data/remote/model/response/GetMoviePostersResponse.kt

data class GetMoviePostersResponse(
    @SerializedName("id") var movieId: Long = 0,
    @SerializedName("posters") var posters: ArrayList<Poster> = arrayListOf()
)

data class Poster(
    @SerializedName("file_path") var filePath: String? = null
)
