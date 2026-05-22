package com.example.android.themoviesapp.data.local.preferences

// data/local/preferences/AppPreferencesImpl.kt

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

class AppPreferencesImpl(
    context: Context
) : AppPreferences {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    override suspend fun saveAuthToken(token: String): Boolean {
        return withContext(Dispatchers.IO) {
            prefs.edit()
                .putString(KEY_AUTH_TOKEN, token)
                .commit()
        }
    }

    override suspend fun getAuthToken(): String? {
        return withContext(Dispatchers.IO) {
            prefs.getString(KEY_AUTH_TOKEN, null)
        }
    }

    override suspend fun clearAuthToken(): Boolean {
        return withContext(Dispatchers.IO) {
            prefs.edit()
                .remove(KEY_AUTH_TOKEN)
                .commit()
        }
    }

    override suspend fun setOnboardingComplete(isComplete: Boolean): Boolean {
        return withContext(Dispatchers.IO) {
            prefs.edit()
                .putBoolean(KEY_ONBOARDING_COMPLETE, isComplete)
                .commit()
        }
    }

    override suspend fun isOnboardingComplete(): Boolean {
        return withContext(Dispatchers.IO) {
            prefs.getBoolean(KEY_ONBOARDING_COMPLETE, false)
        }
    }

    override suspend fun saveLastRefreshTime(time: Long): Boolean {
        return withContext(Dispatchers.IO) {
            prefs.edit()
                .putLong(KEY_LAST_REFRESH_TIME, time)
                .commit()
        }
    }

    override suspend fun getLastRefreshTime(): Long? {
        return withContext(Dispatchers.IO) {
            if (prefs.contains(KEY_LAST_REFRESH_TIME)) {
                prefs.getLong(KEY_LAST_REFRESH_TIME, 0L)
            } else null
        }
    }

    override suspend fun saveSelectedLanguage(language: String): Boolean {
        return withContext(Dispatchers.IO) {
            prefs.edit()
                .putString(KEY_SELECTED_LANGUAGE, language)
                .commit()
        }
    }

    override suspend fun getSelectedLanguage(): String? {
        return withContext(Dispatchers.IO) {
            prefs.getString(KEY_SELECTED_LANGUAGE, null)
        }
    }

    companion object {
        private const val PREFS_NAME               = "movies_app_prefs"
        private const val KEY_AUTH_TOKEN           = "auth_token"
        private const val KEY_ONBOARDING_COMPLETE  = "onboarding_complete"
        private const val KEY_LAST_REFRESH_TIME   = "last_refresh_time"
        private const val KEY_SELECTED_LANGUAGE    = "selected_language"
    }
}