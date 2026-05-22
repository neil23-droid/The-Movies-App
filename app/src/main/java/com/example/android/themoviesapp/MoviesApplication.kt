package com.example.android.themoviesapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// MoviesApplication.kt

@HiltAndroidApp
class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // app level initialisation goes here
    }
}