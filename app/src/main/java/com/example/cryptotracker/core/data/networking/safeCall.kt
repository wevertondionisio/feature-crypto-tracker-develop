package com.example.cryptotracker.core.data.networking

import com.example.cryptotracker.core.domain.util.NetworkError
import com.example.cryptotracker.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.coroutineContext

/**
 * Performs a safe network call with error handling and type conversion.
 *
 * Features:
 * - Automatic error handling for common network issues
 * - Serialization error handling
 * - Coroutine cancellation support
 * - Type-safe response conversion
 *
 * Error cases handled:
 * - UnresolvedAddressException -> NO_INTERNET
 * - SerializationException -> SERIALIZATION
 * - Other exceptions -> NO_INTERNET (with coroutine cancellation check)
 *
 * @param T The expected type of the successful response
 * @param execute Lambda function that executes the HTTP request and returns HttpResponse
 * @return Result<T, NetworkError> containing either the successful response or a NetworkError
 */
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute.invoke()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        return Result.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.NO_INTERNET)
    }
    return responseToResult(response)
}