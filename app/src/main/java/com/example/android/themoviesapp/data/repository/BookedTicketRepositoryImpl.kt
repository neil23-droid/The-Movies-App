package com.example.android.themoviesapp.data.repository

import com.example.android.themoviesapp.data.local.datasource.BookedTicketLocalDataSource
import com.example.android.themoviesapp.data.local.mapper.toDomain
import com.example.android.themoviesapp.data.local.mapper.toEntity
import com.example.android.themoviesapp.data.local.result.LocalResult
import com.example.android.themoviesapp.domain.model.BookedTicket
import com.example.android.themoviesapp.domain.result.DomainResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Implementation
class BookedTicketRepositoryImpl(
    private val local: BookedTicketLocalDataSource, // depends on interface, not impl
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : BookedTicketRepository {

    override suspend fun getBookedTickets(): DomainResult<List<BookedTicket>> = withContext(dispatcher) {
        return@withContext when (val bookingHistoryResult = local.getBookedTickets()) {
            is LocalResult.Success -> DomainResult.Success(
                bookingHistoryResult.data.map { it.toDomain() }
            )

            is LocalResult.Empty ->{
                DomainResult.Empty
            }

            is LocalResult.Error -> DomainResult.Error(                // ← HTTP code dropped here
                message = bookingHistoryResult.message,
                throwable = bookingHistoryResult.throwable
            )
        }
    }

    override suspend fun saveBookedTicket(
        ticket: BookedTicket
    ): DomainResult<Long> = withContext(dispatcher){
        val savedResult = local.saveBookedTicket(ticket.toEntity())
        return@withContext when (savedResult) {
            is LocalResult.Success -> DomainResult.Success(
                savedResult.data,
            )
            is LocalResult.Empty ->{
                DomainResult.Empty
            }
            is LocalResult.Error -> DomainResult.Error(
                message = savedResult.message,
                throwable = savedResult.throwable
            )
        }
    }
}