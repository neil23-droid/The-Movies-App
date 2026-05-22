package com.example.android.themoviesapp.domain.usecase

import com.example.android.themoviesapp.data.repository.BookedTicketRepository
import com.example.android.themoviesapp.domain.model.BookedTicket
import com.example.android.themoviesapp.domain.result.DomainResult
import javax.inject.Inject

// domain/usecase/BookTicketUseCase.kt

class BookTicketUseCase @Inject constructor(
    private val bookedTicketRepository: BookedTicketRepository
) {
    suspend fun bookTicket(ticket: BookedTicket): DomainResult<Long> {

        // business rule 1 — location must be selected
        if (ticket.selectedLocation.isNullOrBlank()) {
            return DomainResult.Error(message = "Please select a location")
        }

        // business rule 2 — cinema must be selected
        if (ticket.selectedCinema.isNullOrBlank()) {
            return DomainResult.Error(message = "Please select a cinema")
        }

        // business rule 3 — seat must be selected
        if (ticket.selectedSeat.isNullOrBlank()) {
            return DomainResult.Error(message = "Please select a seat")
        }

        // business rule 4 — movie title must exist
        if (ticket.title.isNullOrBlank()) {
            return DomainResult.Error(message = "Invalid movie selection")
        }

        // all valid — save to repository
        return bookedTicketRepository.saveBookedTicket(ticket)
    }
}