package com.example.android.themoviesapp.data.local.datasource

import com.example.android.themoviesapp.data.local.dao.BookedTicketHistoryDao
import com.example.android.themoviesapp.data.local.entities.BookedTicketHistoryTable
import com.example.android.themoviesapp.data.local.result.LocalResult

// Implementation — maps exactly to your DAO methods
class BookedTicketLocalDataSourceImpl(
    private val bookedTicketHistoryDao: BookedTicketHistoryDao
) : BookedTicketLocalDataSource {
    override suspend fun getBookedTickets(): LocalResult<List<BookedTicketHistoryTable>> {
        return try {
            val bookedTicketsHistory =
                bookedTicketHistoryDao.getBookedTicketHistoryList()  // your exact DAO method
            if (bookedTicketsHistory.isEmpty())
                LocalResult.Empty
            else
                LocalResult.Success(bookedTicketsHistory)
        } catch (e: Exception) {
            LocalResult.Error(
                message = "Failed to fetch movies from database.",
                throwable = e
            )
        }
    }

    override suspend fun saveBookedTicket(
        ticket: BookedTicketHistoryTable
    ): LocalResult<Long> {
        return try {
            val id = bookedTicketHistoryDao.insertTicket(ticket)
            if (id > 0)
                LocalResult.Success(id)
            else
                LocalResult.Error(message = "Failed to save ticket")
        } catch (e: Exception) {
            LocalResult.Error(
                message = "Database error while saving ticket",
                throwable = e
            )
        }
    }
}