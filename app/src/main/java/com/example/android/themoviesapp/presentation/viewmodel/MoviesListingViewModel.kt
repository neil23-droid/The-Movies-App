package com.example.android.themoviesapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.themoviesapp.domain.result.DomainResult
import com.example.android.themoviesapp.domain.usecase.DisplayUpcomingMoviesUseCase
import com.example.android.themoviesapp.presentation.mapper.toMoviesListUiModel
import com.example.android.themoviesapp.presentation.models.MoviesListUiModel
import com.example.android.themoviesapp.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// presentation/viewmodel/MoviesListViewModel.kt
@HiltViewModel
class MoviesListingViewModel @Inject constructor(
    private val getUpcomingMoviesUseCase: DisplayUpcomingMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<MoviesListUiModel>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<MoviesListUiModel>>> = _uiState.asStateFlow()

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
}