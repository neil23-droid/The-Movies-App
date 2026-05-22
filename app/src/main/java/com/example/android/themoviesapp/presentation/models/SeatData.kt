package com.example.android.themoviesapp.presentation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeatData (
    var id:String = "",
    var SeatNumber:String=""
): Parcelable