@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.example.cryptotracker.core.navigation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptotracker.core.data.networking.toErrorString
import com.example.cryptotracker.crypto.presentation.CoinDetailScreen
import com.example.cryptotracker.crypto.presentation.CoinListAction
import com.example.cryptotracker.crypto.presentation.CoinListScreen
import com.example.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import com.example.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import com.example.cryptotracker.crypto.presentation.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

/**
 * Adaptive layout composable that implements a list-detail pattern for cryptocurrency display.
 * Uses Material3's adaptive navigation components for responsive layouts across different screen sizes.
 *
 * Features:
 * - Responsive layout adaptation between list and detail views
 * - Smooth animations during transitions
 * - Integration with CoinListViewModel for state management
 * - Error handling with toast messages
 * - Event-based navigation
 *
 * @param modifier Optional modifier for customizing the layout
 * @param viewModel ViewModel instance for managing cryptocurrency data and UI state
 */
@Composable
fun AdaptiveCoinListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: CoinListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    ObserveAsEvents(events = viewModel.events) { event ->
        when(event) {
            is CoinListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toErrorString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CoinListScreen(
                    state = state,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when(action) {
                            is CoinListAction.OnCoinClick -> {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail
                                )
                            }
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                CoinDetailScreen(state = state)
            }
        },
        modifier = modifier
    )
}