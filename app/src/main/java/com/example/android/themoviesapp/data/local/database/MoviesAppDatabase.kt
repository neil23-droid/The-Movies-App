package com.example.android.themoviesapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.themoviesapp.data.local.dao.BookedTicketHistoryDao
import com.example.android.themoviesapp.data.local.dao.MoviesDao

import com.example.android.themoviesapp.data.local.database.LongConverter
import com.example.android.themoviesapp.data.local.entities.BookedTicketHistoryTable
import com.example.android.themoviesapp.data.local.entities.UpcomingMoviesTable


@Database(entities = [UpcomingMoviesTable::class, BookedTicketHistoryTable::class], version = 2,  exportSchema = false)
@TypeConverters(LongConverter::class)
abstract class MoviesAppDatabase : RoomDatabase() {

    abstract val moviesDao: MoviesDao
    abstract val bookedTicketHistoryDao: BookedTicketHistoryDao

    companion object {
        // 1. @Volatile is CRITICAL.
        // It ensures that changes made by one thread to INSTANCE are
        // immediately visible to all other threads.
        @Volatile
        private var INSTANCE: MoviesAppDatabase? = null

        fun getInstance(context: Context): MoviesAppDatabase {
            // 2. First check (No locking)
            // If the instance exists, return it immediately without synchronizing.
            return INSTANCE ?: synchronized(this) {
                // 3. Second check (Locking)
                // If another thread initialized it while we were waiting for the lock,
                // use that instance instead of creating a new one.
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MoviesAppDatabase::class.java,
                    "movies_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it } // 4. Assign and return
            }
        }
    }


}