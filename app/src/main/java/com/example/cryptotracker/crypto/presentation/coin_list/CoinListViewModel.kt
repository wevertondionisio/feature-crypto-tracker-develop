package com.example.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptotracker.core.domain.util.onError
import com.example.cryptotracker.core.domain.util.onSuccess
import com.example.cryptotracker.crypto.domain.CoinDataSource
import com.example.cryptotracker.crypto.domain.model.ui.CoinUi
import com.example.cryptotracker.crypto.domain.model.ui.toCoinUi
import com.example.cryptotracker.crypto.presentation.CoinListAction
import com.example.cryptotracker.crypto.presentation.coin_detail.model.DataPoint
import com.example.cryptotracker.crypto.presentation.coin_list.model.CoinListState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * ViewModel responsible for managing the cryptocurrency list screen's state and business logic.
 *
 * This ViewModel handles:
 * - Loading and refreshing the list of cryptocurrencies
 * - Managing the UI state for the coin list
 * - Processing user actions and updating the UI accordingly
 *
 * @param coinDataSource The data source for fetching cryptocurrency information
 */
class CoinListViewModel(
    private val coinDataSource: CoinDataSource
): ViewModel() {

    /** Holds the current UI state of the coin list screen */
    private val _state = MutableStateFlow(CoinListState())

    /**
     * Public state flow that emits updates to the UI state.
     * Automatically loads coins when collecting starts.
     */
    val state = _state
        .onStart {
            loadCoins()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )

    /** Channel for one-time UI events like errors */
    private val _events = Channel<CoinListEvent>()

    /** Flow of UI events that should be handled once */
    val events = _events.receiveAsFlow()

    /**
     * Handles user actions performed in the UI.
     *
     * @param action The action performed by the user
     */
    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {
                selectCoin(action.coinUi)
            }
        }
    }

    /**
     * Selects a cryptocurrency and loads its price history.
     * Updates the UI state with the selected coin and its historical price data.
     *
     * @param coinUi The coin selected by the user
     */
    private fun selectCoin(coinUi: CoinUi) {
        _state.update { it.copy(selectedCoin = coinUi) }
        viewModelScope.launch {
            coinDataSource
                .getCoinHistory(
                    coinId = coinUi.id,
                    start = ZonedDateTime.now().minusDays(5),
                    end = ZonedDateTime.now()
                )
                .onSuccess { history ->
                    val dataPoints = history
                        .sortedBy { it.dateTime }
                        .map {
                            DataPoint(
                                x = it.dateTime.hour.toFloat(),
                                y = it.priceUsd.toFloat(),
                                xLabel = DateTimeFormatter
                                    .ofPattern("ha\nM/d")
                                    .format(it.dateTime)
                            )
                        }
                    _state.update {
                        it.copy(
                            selectedCoin = it.selectedCoin?.copy(
                                coinPriceHistory = dataPoints
                            )
                        )
                    }

                }
                .onError {
                    _events.send(
                        CoinListEvent.Error(it)
                    )
                }
        }
    }

    /**
     * Loads the list of available cryptocurrencies.
     * Updates the UI state with loading status and results.
     * Emits error events if the loading fails.
     */
    private fun loadCoins() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            coinDataSource
            .getCoins()
            .onSuccess { coins ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        coins = coins.map {
                            coin-> coin.toCoinUi()
                        }
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                    )
                }
                _events.send(
                    CoinListEvent.Error(error)
                )
            }
        }
    }
}