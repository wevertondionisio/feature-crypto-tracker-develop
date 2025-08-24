package com.example.cryptotracker.crypto.domain

import com.example.cryptotracker.core.data.networking.NetworkError
import com.example.cryptotracker.core.data.networking.Result
import com.example.cryptotracker.crypto.domain.model.Coin

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}