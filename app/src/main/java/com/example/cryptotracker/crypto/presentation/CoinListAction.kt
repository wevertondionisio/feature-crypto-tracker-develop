package com.example.cryptotracker.crypto.presentation

import com.example.cryptotracker.crypto.domain.model.ui.CoinUi

/**
 * Represents all possible user actions in the cryptocurrency list screen.
 * Used to handle user interactions in a type-safe way.
 */
sealed interface CoinListAction {
    /**
     * Represents the action of selecting a cryptocurrency from the list.
     *
     * @property coinUi The selected cryptocurrency UI model
     */
    data class OnCoinClick(val coinUi: CoinUi): CoinListAction
}