package com.example.cryptotracker.crypto.presentation.coin_detail.model

import java.text.NumberFormat
import java.util.Locale

/**
 * Represents a numeric value with its unit for display on the chart.
 * Handles intelligent number formatting based on the value's magnitude.
 *
 * Features:
 * - Automatic decimal place adjustment based on value size
 * - Locale-aware number formatting
 * - Unit display (e.g., "$", "%")
 */
data class ValueLabel(
    /** The numeric value to be formatted */
    val value: Float,
    /** The unit symbol to append to the formatted value */
    val unit: String
) {
    /**
     * Formats the value with appropriate decimal places and unit.
     *
     * Formatting rules:
     * - Values > 1000: No decimal places
     * - Values 2-999: 2 decimal places
     * - Values < 2: 3 decimal places
     *
     * @return The formatted string with the unit appended
     */
    fun formatted(): String {
        val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
            val fractionDigits = when {
                value > 1000 -> 0
                value in 2f..999f -> 2
                else -> 3
            }
            maximumFractionDigits = fractionDigits
            minimumFractionDigits = 0
        }
        return "${formatter.format(value)}$unit"
    }
}
