package com.example.cryptotracker.crypto.domain.model

import java.time.ZonedDateTime

/**
 * Represents a historical price point for a cryptocurrency.
 *
 * This data class stores the price information at a specific point in time,
 * used primarily for tracking price history and creating price charts.
 */
data class CoinPrice(
    /** The price of the cryptocurrency in USD at the given time */
    val priceUsd: Double,
    /** The timestamp when this price was recorded */
    val dateTime: ZonedDateTime
)
