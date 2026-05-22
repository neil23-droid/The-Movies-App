package com.example.android.themoviesapp.presentation.ui.location_details

// presentation/uistate/BookTicketUiState.kt

sealed class BookTicketUiState {
    data object Idle : BookTicketUiState()        // ← waiting for user input
    data object Loading : BookTicketUiState()     // ← saving to DB
    data object Success : BookTicketUiState()     // ← booking confirmed
    data class Error(
        val message: String
    ) : BookTicketUiState()                       // ← validation or DB error
}