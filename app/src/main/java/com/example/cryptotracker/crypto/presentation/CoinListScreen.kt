package com.example.cryptotracker.crypto.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptotracker.crypto.presentation.coin_list.components.CoinListItem
import com.example.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.example.cryptotracker.crypto.presentation.coin_list.model.CoinListState

/**
 * Main screen composable that displays a list of cryptocurrencies.
 *
 * Features:
 * - Lazy loading list with efficient recycling
 * - Loading state indication
 * - Interactive coin items with click handling
 * - Visual dividers between items
 * - Supports both light and dark themes
 * - Handles empty and loading states
 *
 * @param state Current state of the coin list UI
 * @param onAction Callback for handling user actions (e.g., coin selection)
 * @param modifier Optional modifier for customizing the layout
 */
@Composable
fun CoinListScreen(
    state: CoinListState,
    modifier: Modifier = Modifier,
    onAction: (CoinListAction) -> Unit = {},
) {
    // Loading state display
    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        // Main list content
        val navigationBarsPadding = WindowInsets.navigationBars.asPaddingValues()
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.coins) { coin ->
                CoinListItem(
                    coinUi = coin,
                    onClick = {
                        onAction(CoinListAction.OnCoinClick(coin))
                    },
                    modifier = Modifier.fillMaxSize()
                )
                HorizontalDivider()
            }
            item {
                Spacer(Modifier.padding(navigationBarsPadding))
            }
        }
    }
}

/**
 * Preview composable for CoinListScreen.
 * Shows how the screen looks with sample data.
 */
@Preview
@Composable
private fun CoinListScreenPrev() {
    CoinListScreen(
        state = CoinListState(
            coins = (1..100).map {
                previewCoin.copy(id = it.toString())
            }
        ),
        modifier = Modifier.background(color = Color.White),
        onAction = {},
    )
}
