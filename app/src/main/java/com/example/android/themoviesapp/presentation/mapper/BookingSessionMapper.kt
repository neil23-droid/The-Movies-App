package com.example.android.themoviesapp.presentation.mapper

import com.example.android.themoviesapp.domain.model.BookedTicket
import com.example.android.themoviesapp.domain.model.MovieDetail
import com.example.android.themoviesapp.presentation.models.BookingSessionModel
import com.example.android.themoviesapp.presentation.models.MovieDetailsUiModel

// presentation/mapper/BookingSessionMapper.kt


// ✅ Source is domain model — raw data
fun MovieDetail.toBookingSessionModel(): BookingSessionModel {
    return BookingSessionModel(
        movieId          = this.movieId,
        title            = this.title ?: "",
        originalTitle    = this.originalTitle ?: "",
        overview         = this.overview ?: "",
        posterPath       = this.posterPath ?: "",
        backdropPath     = this.backdropPath ?: "",
        releaseDate      = this.releaseDate ?: "",
        popularity       = this.popularity,
        originalLanguage = this.originalLanguage ?: "",
        adult            = this.adult
    )
}

// BookingSessionModel → BookedTicket (domain)
fun BookingSessionModel.toDomain(): BookedTicket {
    return BookedTicket(
        title            = this.title,
        originalTitle    = this.originalTitle,
        overview         = this.overview,
        posterPath       = this.posterPath,
        backdropPath     = this.backdropPath,
        releaseDate      = this.releaseDate,
        popularity       = this.popularity,
        originalLanguage = this.originalLanguage,
        adult            = this.adult,
        selectedLocation = this.selectedLocation?.place?:"",
        selectedCinema   = this.selectedCinema?.cinemaName?:"",
        selectedSeat     = this.selectedSeat?.SeatNumber?:""
    )
}