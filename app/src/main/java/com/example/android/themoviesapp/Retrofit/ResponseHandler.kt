package com.example.android.themoviesapp.Retrofit

sealed class ResponseHandler<out T>() {

    data class Success<out T>(val status: ResposneStatus= ResposneStatus.SUCCESS, val data: T?, val message: String?): ResponseHandler<T>()
    data class Error<out T>(val status: ResposneStatus = ResposneStatus.FAIL, val data: T?, val message: String?): ResponseHandler<Nothing>()
}



enum class ResposneStatus {
    SUCCESS,
    FAIL
}