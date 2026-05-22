package com.example.android.themoviesapp.data.local.mapper

import com.example.android.themoviesapp.data.local.entities.BookedTicketHistoryTable
import com.example.android.themoviesapp.domain.model.BookedTicket

// data/local/mapper/BookedTicketEntityMapper.kt

fun BookedTicketHistoryTable.toDomain(): BookedTicket {
    return BookedTicket(
        // movie details
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate,
        popularity = this.popularity,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        adult = this.adult,

        // booking specific
        selectedLocation = this.selectedLocation,
        selectedCinema = this.selectedCinema,
        selectedSeat = this.selectedSeat
    )
}

fun BookedTicket.toEntity(): BookedTicketHistoryTable {
    return BookedTicketHistoryTable(
        adult = this.adult,
        backdropPath = this.backdropPath,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        selectedLocation = this.selectedLocation,
        selectedCinema = this.selectedCinema,
        selectedSeat = this.selectedSeat
    )
}