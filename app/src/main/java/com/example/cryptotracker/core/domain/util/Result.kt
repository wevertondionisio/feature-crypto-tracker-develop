package com.example.cryptotracker.core.domain.util

/** Type alias for domain-specific errors that implement the Error interface */
typealias DomainError = Error

/**
 * A type-safe representation of an operation result that can be either successful or failed.
 *
 * @param D Type of successful data
 * @param E Type of error, must implement Error interface
 */
sealed interface Result<out D, out E: Error> {
    /** Represents a successful operation containing data */
    data class Success<out D>(val data: D): Result<D, Nothing>
    /** Represents a failed operation containing an error */
    data class Error<out E: DomainError>(val error: E): Result<Nothing, E>
}

/**
 * Maps successful data to a new type while preserving error cases.
 * Useful for transforming operation results while maintaining error handling.
 *
 * @param map Transformation function for successful data
 * @return New Result with transformed data or original error
 */
inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

/**
 * Converts a Result with data to a Result without data (Empty).
 * Useful when you need to discard the success data but maintain error handling.
 *
 * @return EmptyResult preserving only error information
 */
fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

/**
 * Executes an action on successful results while preserving the original Result.
 * Useful for side effects like logging or UI updates on success.
 *
 * @param action Function to execute with successful data
 * @return Original Result unchanged
 */
inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}

/**
 * Executes an action on error results while preserving the original Result.
 * Useful for error handling, logging, or showing error messages to users.
 *
 * @param action Function to execute with the error
 * @return Original Result unchanged
 */
inline fun <T, E: Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}

/** Type alias for Results that don't contain data in the success case */
typealias EmptyResult<E> = Result<Unit, E>