package com.example.android.themoviesapp.di

import android.content.Context
import com.example.android.themoviesapp.data.local.preferences.AppPreferences
import com.example.android.themoviesapp.data.local.preferences.AppPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// di/PreferencesModule.kt

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun provideAppPreferences(
        @ApplicationContext context: Context  // ← Hilt provides context
    ): AppPreferences {
        return AppPreferencesImpl(context)
    }
}
