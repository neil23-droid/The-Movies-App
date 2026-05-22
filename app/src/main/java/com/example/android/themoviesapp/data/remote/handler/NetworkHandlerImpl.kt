package com.example.android.themoviesapp.data.remote.handler


import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

// data/remote/handler/NetworkHandlerImpl.kt

class NetworkHandlerImpl : NetworkHandler {

    override suspend fun <T> handleApiCall(
        call: suspend () -> Response<T>
    ): ResponseHandler<T> =
        try {
            val response = call()
            when {
                response.isSuccessful -> {
                    val body = response.body()
                    if (body == null) ResponseHandler.EmptyResponse
                    else ResponseHandler.Success(body)
                }
                else -> ResponseHandler.Error(
                    message = response.message(),
                    code = response.code()
                )
            }
        } catch (e: CancellationException) {
            throw e                                         // ← never swallow cancellation
        } catch (e: UnknownHostException) {
            ResponseHandler.Error(
                message = "No internet connection.",
                throwable = e
            )
        } catch (e: SocketTimeoutException) {
            ResponseHandler.Error(
                message = "Request timed out.",
                throwable = e
            )
        } catch (e: IOException) {
            ResponseHandler.Error(
                message = "Network error.",
                throwable = e
            )
        } catch (e: Exception) {
            ResponseHandler.Error(
                message = "Unexpected error.",
                throwable = e
            )
        }
}