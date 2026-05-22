package com.example.android.themoviesapp.presentation.models

// presentation/model/BookedTicketUiModel.kt
data class BookedTicketUiModel(

    // display ready movie info
    val title: String,               // "Unknown Movie" if null
    val posterPath: String,          // placeholder if null

    // display ready booking info
    val selectedLocation: String,    // "No location selected" if null
    val selectedCinema: String,      // "No cinema selected" if null
    val selectedSeat: String,        // "No seat selected" if null
)
