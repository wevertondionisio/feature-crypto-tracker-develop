package com.example.cryptotracker.crypto.presentation.coin_list

import com.example.cryptotracker.core.domain.util.NetworkError

/**
 * Represents one-time events that occur in the cryptocurrency list screen.
 * Used for communicating transient events like errors that should only be handled once.
 */
sealed interface CoinListEvent {
    /**
     * Represents a network error event that should be shown to the user.
     *
     * @property error The network error that occurred during an operation
     */
    data class Error(val error: NetworkError) : CoinListEvent
}