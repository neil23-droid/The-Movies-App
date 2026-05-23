package com.example.android.themoviesapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
const val TABLE_NAME_BOOKED_TICKET_HISTORY = "BOOKED_TICKET_HISTORY_TABLE"

@Entity(tableName = TABLE_NAME_BOOKED_TICKET_HISTORY)
data class BookedTicketHistoryTable(
    var adult:Boolean = false,
    var backdropPath: String? = null,
    var originalLanguage: String? = null,
    var originalTitle: String? = null,
    var overview: String? = null,
    var popularity:Double = 0.0,
    var posterPath: String? = null,
    var releaseDate: String? = null,
    var title: String? = null,
    var selectedLocation:String?=null,
    var selectedCinema:String?=null,
    var selectedSeat:String?=null,
    var bookingDate: Long = 0L
){
    @PrimaryKey(autoGenerate = true )var _id:Long = 0
}
