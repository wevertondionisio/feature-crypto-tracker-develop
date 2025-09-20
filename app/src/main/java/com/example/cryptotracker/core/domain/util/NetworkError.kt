package com.example.cryptotracker.core.domain.util

/**
 * Represents various network-related errors that can occur during API communication.
 * Implements the Error interface for type-safe error handling.
 */
enum class NetworkError: Error {
    /** Request took too long to complete and timed out */
    REQUEST_TIMEOUT,

    /** API rate limit exceeded */
    TOO_MANY_REQUESTS,

    /** No internet connection available */
    NO_INTERNET,

    /** Server returned an error response (5xx status codes) */
    SERVER_ERROR,

    /** Failed to parse the API response */
    SERIALIZATION,

    /** Unexpected or unhandled error */
    UNKNOWN,
}