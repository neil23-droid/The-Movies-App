package com.example.android.themoviesapp.presentation.mapper

import com.example.android.themoviesapp.Others.extenstions.formatReleaseDate
import com.example.android.themoviesapp.domain.model.BookedTicket
import com.example.android.themoviesapp.presentation.models.BookingHistoryUiModel


// presentation/mapper/BookedTicketUiMapper.kt
fun BookedTicket.toBookingHistoryUiModel(): BookingHistoryUiModel {
    return BookingHistoryUiModel(
        originalTitle = this.title ?: "Unknown Movie",
        posterPath = this.posterPath ?: "",
        selectedLocation = this.selectedLocation ?: "No location selected",
        selectedCinema = this.selectedCinema ?: "No cinema selected",
        selectedSeat = this.selectedSeat ?: "No seat selected",
        adult = this.adult,
        releaseDate = this.releaseDate.formatReleaseDate()
    )
}
