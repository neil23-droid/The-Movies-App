package com.example.android.themoviesapp.data.remote.handler

import retrofit2.Response

// data/remote/handler/NetworkHandler.kt

interface NetworkHandler {
    suspend fun <T> handleApiCall(call: suspend () -> Response<T>): ResponseHandler<T>
}