package com.example.android.themoviesapp.data.local.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// data/local/database/MoviesDatabaseMigrations.kt

object MoviesDatabaseMigrations {

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
                ALTER TABLE BOOKED_TICKET_HISTORY_TABLE 
                ADD COLUMN bookingDate INTEGER NOT NULL DEFAULT 0
                """
            )
        }
    }

    // future migrations added here
    // val MIGRATION_3_4 = object : Migration(3, 4) { ... }
    // val MIGRATION_4_5 = object : Migration(4, 5) { ... }
}