package com.example.android.themoviesapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigationevent.NavigationEvent
import com.example.android.themoviesapp.domain.result.DomainResult
import com.example.android.themoviesapp.domain.usecase.DisplayUpcomingMoviesUseCase
import com.example.android.themoviesapp.presentation.mapper.toMoviesListUiModel
import com.example.android.themoviesapp.presentation.models.MoviesListUiModel
import com.example.android.themoviesapp.presentation.common.UiState
import com.example.android.themoviesapp.presentation.ui.movies.MoviesListNavigationEvent
import com.example.android.themoviesapp.presentation.ui.movies.MoviesListUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// presentation/viewmodel/MoviesListViewModel.kt
@HiltViewModel
class MoviesListingViewModel @Inject constructor(
    private val getUpcomingMoviesUseCase: DisplayUpcomingMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<MoviesListUiModel>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<MoviesListUiModel>>> = _uiState.asStateFlow()

    // Channel for one shot navigation events
    private val _navigationEvent = Channel<MoviesListNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = when (val result = getUpcomingMoviesUseCase()) {  // ← invoke()
                is DomainResult.Success -> UiState.Success(
                    result.data.map { it.toMoviesListUiModel() }
                )
                is DomainResult.Error   -> UiState.Error(result.message)
                is DomainResult.Empty   -> UiState.Empty
                is DomainResult.Loading -> UiState.Loading
            }
        }
    }

    // single entry point for ALL user events
    fun onEvent(event: MoviesListUiEvent) {
        when (event) {
            is MoviesListUiEvent.OnMovieClicked       -> handleOnMovieClick(event.selectedMovie)
            is MoviesListUiEvent.OnRefreshClicked     -> loadMovies()
        }
    }

    private fun handleOnMovieClick(selectedMovie: MoviesListUiModel) {
        // navigation handled via one shot event
        _navigationEvent.trySend(MoviesListNavigationEvent.ToMovieDetail(selectedMovie))
    }
}