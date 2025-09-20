package com.example.cryptotracker.core.data.networking

import com.example.cryptotracker.core.domain.util.NetworkError
import com.example.cryptotracker.core.domain.util.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

/**
 * Converts an HTTP response to a type-safe Result object with proper error handling.
 *
 * Status code handling:
 * - 200-299: Success with body conversion
 * - 408: Request timeout error
 * - 429: Rate limit exceeded error
 * - 500-599: Server error
 * - Others: Unknown error
 *
 * Features:
 * - Automatic body deserialization for success cases
 * - Specific error types for common HTTP error codes
 * - Safe body transformation with serialization error handling
 *
 * @param T The expected type of the response body
 * @param response The HTTP response to process
 * @return Result containing either the deserialized response or a specific NetworkError
 */
suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, NetworkError> {
    return when(response.status.value) {
        in 200 .. 299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error(NetworkError.SERIALIZATION)
            }
        }
        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
        in 500 .. 599 -> Result.Error(NetworkError.SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN)
    }
}