package com.example.cryptotracker.core.data.networking

import android.util.Log
import io.ktor.client.plugins.logging.Logger

/**
 * Custom logger implementation for Ktor HTTP client.
 * This class handles logging of HTTP requests and responses for debugging purposes.
 */
class KtorLogger : Logger {
    /**
     * Logs messages from Ktor HTTP client operations.
     *
     * @param message The message to be logged from the HTTP client
     */
    override fun log(message: String) {
        Log.d("CryptoAPI", message)
    }
}
