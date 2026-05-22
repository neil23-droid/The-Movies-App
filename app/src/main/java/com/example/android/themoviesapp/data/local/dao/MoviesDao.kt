package com.example.android.themoviesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.themoviesapp.data.local.entities.UpcomingMoviesTable


@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(videos: ArrayList<UpcomingMoviesTable>):List<Long>

    @Query("Select * FROM UPCOMING_MOVIES_TABLE")
    suspend fun getUpComingMoviesList():List<UpcomingMoviesTable>
}