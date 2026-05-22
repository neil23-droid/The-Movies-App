package com.example.android.themoviesapp.presentation.mapper

import com.example.android.themoviesapp.domain.model.MoviePoster
import com.example.android.themoviesapp.presentation.models.MoviePostersUiModel


// MoviePoster → List<MoviePostersUiModel>
fun MoviePoster.toMoviePostersUiModelList(): List<MoviePostersUiModel> {
    return this.filePaths.map { filePath ->
        MoviePostersUiModel(
            movieId = this.movieId,
            filePath = filePath         // ← one UiModel per poster
        )
    }
}