package com.example.android.themoviesapp.presentation.ui.movies

import com.example.android.themoviesapp.presentation.models.MoviesListUiModel

// presentation/ui/movieslist/MoviesListUiEvent.kt

sealed class MoviesListUiEvent {
    // user actions on this screen
    data class OnMovieClicked(val selectedMovie: MoviesListUiModel) : MoviesListUiEvent()
    data object OnRefreshClicked : MoviesListUiEvent()
}