package com.example.cryptotracker.crypto.data.networking

import com.example.cryptotracker.core.data.networking.NetworkError
import com.example.cryptotracker.core.data.networking.Result
import com.example.cryptotracker.core.data.networking.constructUrl
import com.example.cryptotracker.core.data.networking.map
import com.example.cryptotracker.core.domain.util.safeCall
import com.example.cryptotracker.crypto.domain.CoinDataSource
import com.example.cryptotracker.crypto.data.dto.CoinResponseDto
import com.example.cryptotracker.crypto.data.dto.toCoin
import com.example.cryptotracker.crypto.domain.model.Coin
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
    private val httpClient: HttpClient
): CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets" + "?apiKey=c08f74ff2cd246e121366d35bc8deeacc13736bf738738a033481b7d9d942376")
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }
}