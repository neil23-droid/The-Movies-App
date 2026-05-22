package com.example.android.themoviesapp.BookedTicketHistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.themoviesapp.data.local.database.MoviesAppDatabase
import com.example.android.themoviesapp.data.local.entities.BookedTicketHistoryTable
import kotlinx.coroutines.*

/*
class BookedTicketHistoryViewModel(private val dataSource: MoviesAppDatabase, private val apiPreferences: ApiPreferences):ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope =  CoroutineScope(Dispatchers.Main + viewModelJob)
    private var repository: BookedTicketHistoryRepository = BookedTicketHistoryRepository(dataSource,apiPreferences)



    private var _bookedTicketList:MutableLiveData<ArrayList<BookedTicketHistoryTable>> = MutableLiveData()
    val bookedTicketList:LiveData<ArrayList<BookedTicketHistoryTable>>
        get() = _bookedTicketList




    fun getBookedTicketsList(){
        var bookedTicketsList:ArrayList<BookedTicketHistoryTable> = arrayListOf()
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                bookedTicketsList = repository.getBookedTicketsFromDB()
            }

            _bookedTicketList.postValue(bookedTicketsList)
        }
    }


    override fun onCleared() {
        super.onCleared()
    }
}*/
