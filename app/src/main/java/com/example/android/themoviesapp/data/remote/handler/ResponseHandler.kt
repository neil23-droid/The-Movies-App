package com.example.android.themoviesapp.data.remote.handler

// data/remote/handler/ResponseHandler.kt

sealed class ResponseHandler<out T> {

    data class Success<out T>(
        val data: T,
        val message: String? = null
    ) : ResponseHandler<T>()

    data class Error(
        val message: String = "An unexpected error occurred.",
        val code: Int? = null,
        val throwable: Throwable? = null
    ) : ResponseHandler<Nothing>()

    data object Loading : ResponseHandler<Nothing>()

    data object EmptyResponse : ResponseHandler<Nothing>()
}

