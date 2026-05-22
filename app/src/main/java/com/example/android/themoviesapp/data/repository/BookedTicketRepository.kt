package com.example.android.themoviesapp.data.repository

import com.example.android.themoviesapp.data.local.entities.BookedTicketHistoryTable
import com.example.android.themoviesapp.domain.model.BookedTicket
import com.example.android.themoviesapp.domain.result.DomainResult

// Interface
interface BookedTicketRepository {
    suspend fun getBookedTickets(): DomainResult<List<BookedTicket>>
    suspend fun saveBookedTicket(ticket: BookedTicket): DomainResult<Long>
}