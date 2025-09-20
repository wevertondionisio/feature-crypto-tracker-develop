package com.example.cryptotracker.crypto.presentation.coin_detail.model

/**
 * Represents a single data point on the price chart.
 * Used for plotting price values and their corresponding time labels.
 *
 * @property x The x-coordinate value (usually representing time)
 * @property y The y-coordinate value (usually representing price)
 * @property xLabel The formatted label for the x-axis (e.g., time/date)
 */
data class DataPoint(
    val x: Float,
    val y: Float,
    val xLabel: String
)