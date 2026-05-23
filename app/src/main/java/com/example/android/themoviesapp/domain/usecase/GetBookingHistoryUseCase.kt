package com.example.android.themoviesapp.domain.usecase

import com.example.android.themoviesapp.data.repository.BookedTicketRepository
import com.example.android.themoviesapp.domain.model.BookedTicket
import com.example.android.themoviesapp.domain.result.DomainResult
import javax.inject.Inject

// domain/usecase/GetBookedTicketsUseCase.kt

class GetBookedTicketsUseCase @Inject constructor(
    private val bookedTicketRepository: BookedTicketRepository
) {
    suspend fun getBookedTickets(): DomainResult<List<BookedTicket>> {
        return when (val result = bookedTicketRepository.getBookedTickets()) {
            is DomainResult.Success -> {
                // business rule — show most recent first
                val sorted = result.data
                    .sortedByDescending { it.bookingDate }   // ← belongs here
                DomainResult.Success(sorted)
            }
            is DomainResult.Error   -> result
            is DomainResult.Empty   -> result
            is DomainResult.Loading -> result
        }
    }
}