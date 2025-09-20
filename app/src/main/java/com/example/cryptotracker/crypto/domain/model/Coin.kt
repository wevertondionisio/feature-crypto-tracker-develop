package com.example.cryptotracker.crypto.domain.model

/**
 * Represents a cryptocurrency coin with its market data.
 *
 * This data class holds essential information about a cryptocurrency, including its
 * market performance metrics and identification details.
 */
data class Coin(
    /** Unique identifier of the cryptocurrency */
    val id: String,
    /** Market ranking position of the cryptocurrency */
    val rank: Int,
    /** Full name of the cryptocurrency */
    val name: String,
    /** Trading symbol/ticker of the cryptocurrency */
    val symbol: String,
    /** Total market capitalization in USD */
    val marketCapUsd: Double,
    /** Current price in USD */
    val priceUsd: Double,
    /** Price change percentage in the last 24 hours */
    val changePercent24Hr: Double
)
