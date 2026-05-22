package com.example.android.themoviesapp.presentation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class LocationData(
    var id:String = "",
    var place:String=""
) : Parcelable