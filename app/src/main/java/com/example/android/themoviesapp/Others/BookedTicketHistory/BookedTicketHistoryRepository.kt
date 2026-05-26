/*
package com.example.android.themoviesapp.BookedTicketHistory


import com.example.android.themoviesapp.data.local.database.MoviesAppDatabase
import com.example.android.themoviesapp.Others.ApiPreferences
import com.example.android.themoviesapp.data.local.entities.BookedTicketHistoryTable

class BookedTicketHistoryRepository(private val database: MoviesAppDatabase, private val apiPreferences: ApiPreferences) {


    suspend fun getBookedTicketsFromDB():ArrayList<BookedTicketHistoryTable>{


        val bookedTicketsList =database.bookedTicketHistoryDao.getBookedTicketHistoryList()
        if(bookedTicketsList.isEmpty()){
            return arrayListOf()
        }else{
            return bookedTicketsList as ArrayList<BookedTicketHistoryTable>
        }
    }



}*/
