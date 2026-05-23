package com.example.android.themoviesapp.domain.model

// domain/model/BookedTicket.kt
data class BookedTicket(
    // movie details
    val title: String?,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val popularity: Double,
    val originalLanguage: String?,
    val originalTitle: String?,
    val adult: Boolean,

    // booking specific
    val selectedLocation: String?,
    val selectedCinema: String?,
    val selectedSeat: String?,
    val bookingDate: Long
)