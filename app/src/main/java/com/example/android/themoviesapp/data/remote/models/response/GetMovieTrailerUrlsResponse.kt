package com.example.android.themoviesapp.data.remote.models.response

import com.google.gson.annotations.SerializedName

// data/remote/model/response/GetMovieTrailerUrlsResponse.kt

data class GetMovieTrailerUrlsResponse(
    @SerializedName("id") var id: Long = 0,
    @SerializedName("results") var urlResults: ArrayList<UrlResult> = arrayListOf()
)

data class UrlResult(
    @SerializedName("iso_639_1") var iso6391: String? = null,
    @SerializedName("iso_3166_1") var iso31661: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("key") var key: String? = null,
    @SerializedName("site") var site: String? = null,
    @SerializedName("size") var size: Long = 0,
    @SerializedName("type") var type: String? = null,
    @SerializedName("official") var official: Boolean = false,
    @SerializedName("published_at") var publishedAt: String? = null,
    @SerializedName("id") var id: String? = null
)