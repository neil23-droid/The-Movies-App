package com.example.android.themoviesapp.presentation.models

sealed class DrawerItem {
    object Header : DrawerItem()
    data class MenuItem( val title: String) : DrawerItem()
    // Add more menu items here as needed
    // object AnotherMenuItem : MenuItem()
}