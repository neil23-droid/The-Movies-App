package com.example.android.themoviesapp.data.local.datasource

import com.example.android.themoviesapp.data.local.entities.UpcomingMoviesTable
import com.example.android.themoviesapp.data.local.result.LocalResult


// Interface
interface MovieLocalDataSource {
    suspend fun getUpcomingMovies(): LocalResult<List<UpcomingMoviesTable>>
    suspend fun saveUpcomingMovies(movies: ArrayList<UpcomingMoviesTable>): LocalResult<List<Long>>
}