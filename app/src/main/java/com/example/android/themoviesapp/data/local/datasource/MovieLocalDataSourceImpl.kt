package com.example.android.themoviesapp.data.local.datasource

import com.example.android.themoviesapp.data.local.database.MoviesAppDatabase
import com.example.android.themoviesapp.data.local.dao.MoviesDao
import com.example.android.themoviesapp.data.local.entities.UpcomingMoviesTable
import com.example.android.themoviesapp.data.local.result.LocalResult

// Implementation — maps exactly to your DAO methods
class MovieLocalDataSourceImpl(
    private val moviesDao: MoviesDao
) : MovieLocalDataSource {

    override suspend fun getUpcomingMovies(): LocalResult<List<UpcomingMoviesTable>> {
        return try {
            val movies = moviesDao.getUpComingMoviesList()
            if (movies.isEmpty()) LocalResult.Empty
            else LocalResult.Success(movies)
        } catch (e: Exception) {
            LocalResult.Error(
                message = "Failed to fetch movies from database.",
                throwable = e
            )
        }
    }

    override suspend fun saveUpcomingMovies(
        movies: ArrayList<UpcomingMoviesTable>
    ): LocalResult<List<Long>> {
        return try {
            val result = moviesDao.insertAll(movies) // your exact DAO method
            LocalResult.Success(result)
        } catch (e: Exception) {
            LocalResult.Error(
                message = "Failed to save movies to database.",
                throwable = e
            )
        }
    }
}