package com.example.android.themoviesapp.BookedTicketHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.themoviesapp.data.local.database.MoviesAppDatabase

/*
class BookedTicketHistoryViewModelFactory(private val database: MoviesAppDatabase, private val apiPreferences: ApiPreferences) :
    ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(BookedTicketHistoryViewModel::class.java)) {
            return BookedTicketHistoryViewModel(database,apiPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/
