package com.example.android.themoviesapp.presentation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CinemaData (
    var id:String = "",
    var cinemaName:String=""
): Parcelable