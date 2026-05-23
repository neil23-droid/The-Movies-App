package com.example.android.themoviesapp.presentation.models

// presentation/model/BookingHistoryUiModel.kt

data class BookingHistoryUiModel(
    val originalTitle: String ="",
    val posterPath: String="",         // ← "" if null
    val releaseDate: String="",        // ← "Mar 15, 2024" formatted
    val selectedLocation: String="",   // ← "No location" if null
    val selectedCinema: String="",     // ← "No cinema" if null
    val selectedSeat: String="" ,      // ← "No seat" if null
    val adult: Boolean = false,
    val bookingDate: String=""
)
