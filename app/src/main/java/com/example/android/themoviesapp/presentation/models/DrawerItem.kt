package com.example.android.themoviesapp.presentation.models

import androidx.annotation.StringRes
import com.example.android.themoviesapp.R

sealed class DrawerItem {
    object Header : DrawerItem()

    sealed class MenuItem(@StringRes val titleRes: Int) : DrawerItem() {
        object BookedTicketHistory : MenuItem(R.string.booked_ticket_history)
    }
}