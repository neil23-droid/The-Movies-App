package com.example.android.themoviesapp.presentation.common

sealed class UiState<out T> {

    data class Success<out T>(
        val data: T,
        val message: String? = null
    ) : UiState<T>()

    data class Error(
        val message: String = "Something went wrong.",
        val cause: Throwable? = null // ← no throwable, UI doesn't need exception details
    ) : UiState<Nothing>()

    data object Loading : UiState<Nothing>()

    data object Empty : UiState<Nothing>()
}