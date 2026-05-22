package com.example.android.themoviesapp.data.local.datasource

import com.example.android.themoviesapp.data.local.entities.BookedTicketHistoryTable
import com.example.android.themoviesapp.data.local.result.LocalResult


// Interface
interface BookedTicketLocalDataSource {
    suspend fun getBookedTickets(): LocalResult<List<BookedTicketHistoryTable>>
    suspend fun saveBookedTicket(ticket: BookedTicketHistoryTable): LocalResult<Long>
}