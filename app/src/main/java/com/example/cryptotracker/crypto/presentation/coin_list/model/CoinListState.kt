package com.example.cryptotracker.crypto.presentation.coin_list.model

import androidx.compose.runtime.Immutable
import com.example.cryptotracker.crypto.domain.model.ui.CoinUi

/**
 * Immutable state holder for the cryptocurrency list screen.
 * Represents the complete UI state at any given moment.
 *
 * @property isLoading Indicates whether data is currently being loaded
 * @property coins List of cryptocurrencies to display
 * @property selectedCoin Currently selected cryptocurrency for detailed view, if any
 */
@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val selectedCoin: CoinUi? = null
)
