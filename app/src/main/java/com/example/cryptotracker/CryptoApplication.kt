package com.example.cryptotracker

import android.app.Application
import com.example.cryptotracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Main Application class for the CryptoTracker app.
 *
 * Handles initialization of:
 * - Koin dependency injection
 * - Android context for DI
 * - Logging configuration
 * - Module registration
 */
class CryptoApplication : Application() {
    /**
     * Initializes the application components and dependencies.
     * Sets up Koin for dependency injection with the main app module.
     */
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoApplication)
            androidLogger()

            modules(appModule)
        }
    }
}
