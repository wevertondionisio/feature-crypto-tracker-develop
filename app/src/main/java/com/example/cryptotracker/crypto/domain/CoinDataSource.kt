package com.example.cryptotracker.crypto.domain

import com.example.cryptotracker.core.data.networking.NetworkError
import com.example.cryptotracker.core.data.networking.Result
import com.example.cryptotracker.crypto.domain.model.Coin
import com.example.cryptotracker.crypto.domain.model.CoinPrice
import java.time.ZonedDateTime

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
    suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError>
}