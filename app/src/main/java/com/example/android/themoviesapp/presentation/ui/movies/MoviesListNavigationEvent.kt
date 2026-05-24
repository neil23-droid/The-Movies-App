package com.example.android.themoviesapp.presentation.ui.movies

import com.example.android.themoviesapp.presentation.models.MoviesListUiModel

// presentation/ui/movieslist/MoviesListNavigationEvent.kt

sealed class MoviesListNavigationEvent {
    data class ToMovieDetail(val selectedMovie: MoviesListUiModel) : MoviesListNavigationEvent()
}