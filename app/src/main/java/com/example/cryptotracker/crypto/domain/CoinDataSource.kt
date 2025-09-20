package com.example.cryptotracker.crypto.domain

import com.example.cryptotracker.core.domain.util.NetworkError
import com.example.cryptotracker.core.domain.util.Result
import com.example.cryptotracker.crypto.domain.model.Coin
import com.example.cryptotracker.crypto.domain.model.CoinPrice
import java.time.ZonedDateTime

/**
 * Interface defining the data source operations for cryptocurrency data.
 * Provides methods to fetch coin listings and historical price data from the API.
 */
interface CoinDataSource {
    /**
     * Retrieves a list of all available cryptocurrencies with their current market data.
     *
     * @return Result containing either a list of Coin objects or a NetworkError
     */
    suspend fun getCoins(): Result<List<Coin>, NetworkError>

    /**
     * Retrieves historical price data for a specific cryptocurrency within a given time range.
     * Useful for generating price charts and analyzing price trends.
     *
     * @param coinId Unique identifier of the cryptocurrency
     * @param start Starting timestamp for the historical data
     * @param end Ending timestamp for the historical data
     * @return Result containing either a list of historical price points or a NetworkError
     */
    suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError>
}