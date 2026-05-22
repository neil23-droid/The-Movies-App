package com.example.android.themoviesapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.themoviesapp.domain.model.MovieDetail
import com.example.android.themoviesapp.domain.result.DomainResult
import com.example.android.themoviesapp.domain.usecase.GetMovieDetailUseCase
import com.example.android.themoviesapp.presentation.mapper.toBookingSessionModel
import com.example.android.themoviesapp.presentation.mapper.toMovieDetailUiModel
import com.example.android.themoviesapp.presentation.mapper.toMoviePostersUiModelList
import com.example.android.themoviesapp.presentation.mapper.toMovieTrailerUiModel
import com.example.android.themoviesapp.presentation.models.BookingSessionModel
import com.example.android.themoviesapp.presentation.models.MovieDetailsUiModel
import com.example.android.themoviesapp.presentation.models.MoviePostersUiModel
import com.example.android.themoviesapp.presentation.models.MovieTrailerUiModel
import com.example.android.themoviesapp.presentation.common.UiState
import com.example.android.themoviesapp.presentation.ui.movie_details.MovieDetailsFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// presentation/viewmodel/MovieDetailViewModel.kt

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _movieDetailsUiState = MutableStateFlow<UiState<MovieDetailsUiModel>>(UiState.Loading)
    val movieDetailsUiState: StateFlow<UiState<MovieDetailsUiModel>> = _movieDetailsUiState.asStateFlow()

    private val _moviePostersUiState = MutableStateFlow<UiState<List<MoviePostersUiModel>>>(UiState.Loading)
    val moviePostersUiState: StateFlow<UiState<List<MoviePostersUiModel>>> = _moviePostersUiState.asStateFlow()

    // Safe Args generates a 'fromSavedStateHandle' method (if using latest versions)
    // or you can just access the keys defined in XML directly.
    private val args = MovieDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private var movieId: Long = args.movieId // Type-safe!

    private val _movieTrailerUiState = MutableSharedFlow<UiState<List<MovieTrailerUiModel>>>()
    val movieTrailerUiState: SharedFlow<UiState<List<MovieTrailerUiModel>>> = _movieTrailerUiState.asSharedFlow()


    // ← keep raw domain model for booking
    private var movieDetail: MovieDetail? = null
    fun loadMovieDetail(movieId: Long) {
        viewModelScope.launch {
            _movieDetailsUiState.value = UiState.Loading
            _movieDetailsUiState.value = when (
                val result = getMovieDetailUseCase.getMovieDetail(movieId)
            ) {
                is DomainResult.Success -> {
                    movieDetail = result.data  // ← keep raw domain model for booking
                    UiState.Success(
                        data = result.data.toMovieDetailUiModel()
                    )
                }
                is DomainResult.Error   -> UiState.Error(result.message)
                is DomainResult.Empty   -> UiState.Empty
                is DomainResult.Loading -> UiState.Loading
            }
        }
    }

    fun loadMoviePosters(movieId: Long) {
        viewModelScope.launch {
            _moviePostersUiState.value = UiState.Loading
            _moviePostersUiState.value = when (
                val result = getMovieDetailUseCase.getMoviePosters(movieId)
            ) {
                is DomainResult.Success -> UiState.Success(
                    data = result.data.toMoviePostersUiModelList()
                )
                is DomainResult.Error   -> UiState.Error(result.message)
                is DomainResult.Empty   -> UiState.Empty
                is DomainResult.Loading -> UiState.Loading
            }
        }
    }

    fun getMovieTrailers(movieId: Long){
        viewModelScope.launch {
            viewModelScope.launch {
                _movieTrailerUiState.emit(UiState.Loading)
                val movieTrailerUiState = when (
                    val result = getMovieDetailUseCase.getMovieTrailers(movieId)
                ) {
                    is DomainResult.Success -> UiState.Success(
                        data = result.data.toMovieTrailerUiModel()
                    )
                    is DomainResult.Error   -> UiState.Error(result.message)
                    is DomainResult.Empty   -> UiState.Empty
                    is DomainResult.Loading -> UiState.Loading
                }
                _movieTrailerUiState.emit(movieTrailerUiState)
            }
        }
    }


    // ← returns BookingSessionModel from RAW domain model
    fun getBookingSession(): BookingSessionModel? {
        return movieDetail?.toBookingSessionModel()     // ← domain → session
    }

}