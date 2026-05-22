package com.example.android.themoviesapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.themoviesapp.presentation.models.CinemaData
import com.example.android.themoviesapp.presentation.models.LocationData
import com.example.android.themoviesapp.presentation.models.SeatData
import com.example.android.themoviesapp.domain.result.DomainResult
import com.example.android.themoviesapp.domain.usecase.BookTicketUseCase
import com.example.android.themoviesapp.presentation.mapper.toDomain
import com.example.android.themoviesapp.presentation.models.BookingSessionModel
import com.example.android.themoviesapp.presentation.ui.location_details.BookTicketUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// presentation/viewmodel/BookTicketViewModel.kt

@HiltViewModel
class LocationDetailsViewModel @Inject constructor(
    private val bookTicketUseCase: BookTicketUseCase
) : ViewModel() {

    // dropdown selections — updated as user selects
    private val _selectedLocation = MutableStateFlow<LocationData>(LocationData())
    val selectedLocation: StateFlow<LocationData> = _selectedLocation.asStateFlow()

    private val _selectedCinema = MutableStateFlow<CinemaData>(CinemaData())
    val selectedCinema: StateFlow<CinemaData> = _selectedCinema.asStateFlow()

    private val _selectedSeat = MutableStateFlow<SeatData>(SeatData())
    val selectedSeat: StateFlow<SeatData> = _selectedSeat.asStateFlow()

    // booking confirmation state
    private val _bookingState = Channel<BookTicketUiState>(Channel.BUFFERED)//MutableStateFlow<BookTicketUiState>(BookTicketUiState.Idle)
    val bookingState: Flow<BookTicketUiState> = _bookingState.receiveAsFlow()

    fun onLocationSelected(location: LocationData) {
        _selectedLocation.value = location
    }

    fun onCinemaSelected(cinema: CinemaData) {
        _selectedCinema.value = cinema
    }

    fun onSeatSelected(seat: SeatData) {
        _selectedSeat.value = seat
    }

    fun confirmBooking(bookingSession: BookingSessionModel) {
        viewModelScope.launch {
            if (!isValid()) {
                _bookingState.send(BookTicketUiState.Error("Please fill all fields"))
                return@launch
            }
            _bookingState.send(BookTicketUiState.Loading)

            // update session with selections
            val updatedSession = bookingSession.copy(
                selectedLocation = _selectedLocation.value,
                selectedCinema   = _selectedCinema.value,
                selectedSeat     = _selectedSeat.value
            ).toDomain()                            // ← BookingSessionModel → BookedTicket

           val bookTicketResult  = when (
                val result = bookTicketUseCase.bookTicket(updatedSession)
            ) {
                is DomainResult.Success -> BookTicketUiState.Success
                is DomainResult.Error   -> BookTicketUiState.Error(result.message)
                else                    -> BookTicketUiState.Error("Unexpected error")
            }
            _bookingState.send(bookTicketResult)
        }
    }

    // validation — business rule
    private fun isValid(): Boolean {
        return _selectedLocation.value.id != "0" &&
                _selectedCinema.value.id != "0" &&
                _selectedSeat.value.id != "0"
    }
}