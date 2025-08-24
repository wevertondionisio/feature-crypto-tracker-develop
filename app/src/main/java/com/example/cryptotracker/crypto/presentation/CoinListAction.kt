package com.example.cryptotracker.crypto.presentation

import com.example.cryptotracker.crypto.domain.model.ui.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi): CoinListAction
}