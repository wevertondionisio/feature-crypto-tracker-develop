package com.example.cryptotracker.crypto.presentation.coin_list.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle

data class InfoCardData(
    val title: String,
    val icon: ImageVector,
    val contentColor: Color,
    val formattedText: String,
    val formattedTextStyle: TextStyle
)

