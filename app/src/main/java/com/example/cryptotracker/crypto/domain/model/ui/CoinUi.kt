package com.example.cryptotracker.crypto.domain.model.ui

import androidx.annotation.DrawableRes
import com.example.cryptotracker.R
import com.example.cryptotracker.crypto.domain.model.Coin
import java.text.NumberFormat
import java.util.Locale

data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    @DrawableRes val iconRes: Int
)

data class DisplayableNumber(
    val value: Double,
    val formatted: String
)

fun Coin.toCoinUi(): CoinUi {
    return CoinUi(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd.toDisplayableNumber(),
        priceUsd = priceUsd.toDisplayableNumber(),
        changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
        iconRes = R.drawable.ic_launcher_background
    )
}

fun Double.toDisplayableNumber(): DisplayableNumber{
    val formatted = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        maximumIntegerDigits = 2
        minimumIntegerDigits = 2
    }

    return DisplayableNumber(
        value = this,
        formatted = formatted.format(this)
    )
}