package com.example.cryptotracker.crypto.domain.model.ui

import androidx.annotation.DrawableRes
import com.example.cryptotracker.crypto.domain.model.Coin
import com.example.cryptotracker.crypto.presentation.coin_detail.model.DataPoint
import com.example.cryptotracker.crypto.presentation.util.getDrawableIdForCoin
import java.text.NumberFormat
import java.util.Locale

/**
 * UI representation of a cryptocurrency, optimized for display in the app.
 * Contains formatted values and additional UI-specific properties.
 *
 * @property id Unique identifier of the cryptocurrency
 * @property rank Market rank of the cryptocurrency
 * @property name Full name of the cryptocurrency
 * @property symbol Trading symbol of the cryptocurrency
 * @property marketCapUsd Formatted market capitalization in USD
 * @property priceUsd Formatted current price in USD
 * @property changePercent24Hr Formatted 24-hour price change percentage
 * @property iconRes Resource ID for the cryptocurrency's icon
 * @property coinPriceHistory List of price history data points for the chart
 */
data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    @DrawableRes val iconRes: Int,
    val coinPriceHistory: List<DataPoint> = emptyList()
)

/**
 * Wrapper class for numeric values that need formatting for display.
 * Holds both the raw value and its formatted string representation.
 *
 * @property value The raw numeric value
 * @property formatted The formatted string representation
 */
data class DisplayableNumber(
    val value: Double,
    val formatted: String
)

/**
 * Extension function to convert a domain Coin model to its UI representation.
 * Handles all necessary formatting and resource mapping.
 *
 * @return UI-ready representation of the cryptocurrency
 */
fun Coin.toCoinUi(): CoinUi {
    return CoinUi(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd.toDisplayableNumber(),
        priceUsd = priceUsd.toDisplayableNumber(),
        changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
        iconRes = getDrawableIdForCoin(symbol)
    )
}

/**
 * Extension function to convert a Double to a displayable number format.
 * Uses the device's locale settings for currency formatting.
 *
 * @return Formatted number with currency symbol and proper decimal places
 */
fun Double.toDisplayableNumber(): DisplayableNumber {
    val formatted = NumberFormat.getCurrencyInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    return DisplayableNumber(
        value = this,
        formatted = formatted.format(this)
    )
}