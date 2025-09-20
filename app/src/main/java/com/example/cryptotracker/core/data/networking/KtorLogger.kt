package com.example.cryptotracker.core.data.networking

import android.util.Log
import io.ktor.client.plugins.logging.Logger

class KtorLogger : Logger {
    override fun log(message: String) {
        Log.d("CryptoAPI", message)
    }
}
