package com.example.cryptotracker.crypto.data.networking

import com.example.cryptotracker.core.domain.util.NetworkError
import com.example.cryptotracker.core.domain.util.Result
import com.example.cryptotracker.core.data.networking.constructUrl
import com.example.cryptotracker.core.domain.util.map
import com.example.cryptotracker.core.data.networking.safeCall
import com.example.cryptotracker.crypto.data.dto.CoinHistoryResponseDto
import com.example.cryptotracker.crypto.domain.CoinDataSource
import com.example.cryptotracker.crypto.data.dto.CoinResponseDto
import com.example.cryptotracker.crypto.data.mapping.toCoin
import com.example.cryptotracker.crypto.data.mapping.toCoinPrice
import com.example.cryptotracker.crypto.domain.model.Coin
import com.example.cryptotracker.crypto.domain.model.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Network implementation of CoinDataSource that fetches cryptocurrency data from a remote API.
 *
 * Features:
 * - Asynchronous API communication using Ktor
 * - Safe error handling with Result type
 * - Data mapping from DTOs to domain models
 * - Support for both real-time and historical data
 *
 * @property httpClient Configured Ktor HTTP client for making requests
 */
class RemoteCoinDataSource(
    private val httpClient: HttpClient
): CoinDataSource {
    /**
     * Fetches the current list of cryptocurrencies from the API.
     * Maps the response from DTO to domain models and handles errors.
     *
     * @return Result containing either a list of Coin objects or a NetworkError
     */
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinResponseDto> {
            httpClient.get(
                urlString = constructUrl(
                    url ="/assets"
                )
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }

    /**
     * Fetches historical price data for a specific cryptocurrency.
     * Includes time range filtering and timezone handling.
     *
     * @param coinId The ID of the cryptocurrency to fetch history for
     * @param start Start time for historical data
     * @param end End time for historical data
     * @return Result containing either a list of historical prices or a NetworkError
     */
    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        val endMillis = end
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        return safeCall<CoinHistoryResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }
}