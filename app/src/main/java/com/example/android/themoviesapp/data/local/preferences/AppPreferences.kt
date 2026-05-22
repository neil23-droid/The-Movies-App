package com.example.android.themoviesapp.data.local.preferences

// data/local/preferences/AppPreferences.kt


interface AppPreferences {

    // write operations return Boolean — commit() confirmation
    suspend fun saveAuthToken(token: String): Boolean
    suspend fun clearAuthToken(): Boolean
    suspend fun setOnboardingComplete(isComplete: Boolean): Boolean
    suspend fun saveLastRefreshTime(time: Long): Boolean
    suspend fun saveSelectedLanguage(language: String): Boolean

    // read operations return value only
    suspend fun getAuthToken(): String?
    suspend fun isOnboardingComplete(): Boolean
    suspend fun getLastRefreshTime(): Long?
    suspend fun getSelectedLanguage(): String?
}