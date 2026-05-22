package com.example.android.themoviesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.themoviesapp.data.local.entities.BookedTicketHistoryTable

@Dao
interface BookedTicketHistoryDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: BookedTicketHistoryTable):Long

    @Query("Select * FROM BOOKED_TICKET_HISTORY_TABLE")
    suspend fun getBookedTicketHistoryList():List<BookedTicketHistoryTable>

}