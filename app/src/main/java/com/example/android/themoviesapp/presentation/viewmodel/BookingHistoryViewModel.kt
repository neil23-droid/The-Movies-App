package com.example.android.themoviesapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.themoviesapp.domain.result.DomainResult
import com.example.android.themoviesapp.domain.usecase.GetBookedTicketsUseCase
import com.example.android.themoviesapp.presentation.common.UiState
import com.example.android.themoviesapp.presentation.mapper.toBookingHistoryUiModel
import com.example.android.themoviesapp.presentation.models.BookingHistoryUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// presentation/ui/bookinghistory/BookingHistoryViewModel.kt

@HiltViewModel
class BookingHistoryViewModel @Inject constructor(
    private val getBookedTicketsUseCase: GetBookedTicketsUseCase
) : ViewModel() {

    private val _bookingHistoryUiState = MutableSharedFlow<UiState<List<BookingHistoryUiModel>>>()
    val bookingHistoryUiState: SharedFlow<UiState<List<BookingHistoryUiModel>>> = _bookingHistoryUiState.asSharedFlow()

    fun loadBookedTickets() {
        viewModelScope.launch {
            _bookingHistoryUiState.emit( UiState.Loading)
            val bookingHistoryResult = when (
                val result = getBookedTicketsUseCase.getBookedTickets()
            ) {
                is DomainResult.Success -> UiState.Success(
                    data = result.data.map { it.toBookingHistoryUiModel() }
                )
                is DomainResult.Error   -> UiState.Error(result.message)
                is DomainResult.Empty   -> UiState.Empty
                is DomainResult.Loading -> UiState.Loading
            }
            _bookingHistoryUiState.emit(bookingHistoryResult)
        }
    }
}