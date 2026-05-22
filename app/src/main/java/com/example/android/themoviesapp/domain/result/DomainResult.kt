package com.example.android.themoviesapp.domain.result

// domain/result/DomainResult.kt
// Lives in domain layer — no framework dependencies
// Mirrors ResponseHandler states but without network concerns

sealed class DomainResult<out T> {

    data class Success<out T>(
        val data: T,
        val message: String? = null
    ) : DomainResult<T>()

    data class Error(
        val message: String = "An unexpected error occurred.",
        val throwable: Throwable? = null
        // ← no HTTP code, that's a network concern
    ) : DomainResult<Nothing>()

    data object Loading : DomainResult<Nothing>()

    data object Empty : DomainResult<Nothing>()
}
