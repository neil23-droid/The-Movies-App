package com.example.android.themoviesapp.presentation.mapper

import com.example.android.themoviesapp.domain.model.BookedTicket
import com.example.android.themoviesapp.presentation.models.BookedTicketUiModel

// presentation/mapper/BookedTicketUiMapper.kt
fun BookedTicket.toUiModel(): BookedTicketUiModel {
    return BookedTicketUiModel(
        title = this.title ?: "Unknown Movie",
        posterPath = this.posterPath ?: "",
        selectedLocation = this.selectedLocation ?: "No location selected",
        selectedCinema = this.selectedCinema ?: "No cinema selected",
        selectedSeat = this.selectedSeat ?: "No seat selected"
    )
}
