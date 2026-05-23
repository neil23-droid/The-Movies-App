package com.example.android.themoviesapp.presentation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookingSessionModel(
    val movieId: Long = 0L,
    val title: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",
    val popularity: Double = 0.0,
    val originalLanguage: String = "",
    val adult: Boolean = false,

    // filled later in booking flow
    val selectedLocation: LocationData? = null,
    val selectedCinema: CinemaData? =null,
    val selectedSeat: SeatData? = null,
    val bookingDate: Long = 0L
) : Parcelable