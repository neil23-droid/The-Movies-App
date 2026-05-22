package com.example.android.themoviesapp.data.local.result

// data/local/result/LocalResult.kt

sealed class LocalResult<out T> {

    data class Success<out T>(
        val data: T
    ) : LocalResult<T>()

    data class Error(
        val message: String = "Local storage error.",
        val throwable: Throwable? = null
    ) : LocalResult<Nothing>()

    data object Empty : LocalResult<Nothing>()
    // ← no Loading state, local DB calls are instant
}