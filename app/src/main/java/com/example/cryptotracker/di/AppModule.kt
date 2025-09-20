package com.example.cryptotracker.di

import com.example.cryptotracker.core.data.networking.HttpClientFactory
import com.example.cryptotracker.crypto.data.networking.RemoteCoinDataSource
import com.example.cryptotracker.crypto.domain.CoinDataSource
import com.example.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin dependency injection module configuration for the application.
 * This module provides all the necessary dependencies for the app to function.
 */
val appModule = module {
    /**
     * HTTP Client singleton for making network requests.
     * Uses CIO (Coroutine I/O) engine for efficient networking.
     */
    single { HttpClientFactory.create(CIO.create()) }

    /**
     * Data source for cryptocurrency information.
     * Binds RemoteCoinDataSource implementation to CoinDataSource interface.
     */
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    /**
     * ViewModel for the cryptocurrency list screen.
     */
    viewModelOf(::CoinListViewModel)
}