package com.example.cryptotracker.crypto.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptotracker.R
import com.example.cryptotracker.crypto.domain.model.ui.toDisplayableNumber
import com.example.cryptotracker.crypto.presentation.coin_detail.InfoCard
import com.example.cryptotracker.crypto.presentation.coin_detail.LineChart
import com.example.cryptotracker.crypto.presentation.coin_detail.model.ChartStyle
import com.example.cryptotracker.crypto.presentation.coin_detail.model.DataPoint
import com.example.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.example.cryptotracker.crypto.presentation.coin_list.model.CoinListState

/**
 * A screen composable that displays detailed information about a selected cryptocurrency.
 *
 * Features:
 * - Interactive price chart with animation support
 * - Responsive layout with FlowRow for adaptive content arrangement
 * - Dark/Light theme support
 * - Loading state indication
 * - Scrollable content with verticalScroll
 * - Animated visibility transitions
 * - Adaptive aspect ratio for chart display
 *
 * @param state The state of the coin list, including the selected coin's details
 * @param modifier Optional modifier for customizing the layout
 */
@OptIn(
    ExperimentalLayoutApi::class,
    ExperimentalMaterial3AdaptiveApi::class,
    ExperimentalMaterial3Api::class,
)
@Composable
fun CoinDetailScreen(
    state: CoinListState,
    navigator: ThreePaneScaffoldNavigator<Any>?,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            if (navigator?.scaffoldValue?.secondary != PaneAdaptedValue.Expanded) {
                TopAppBar(
                    title = {
                        Text(state.selectedCoin?.name ?: "Detalhes")
                    },
                    navigationIcon = {
                        IconButton({ navigator?.navigateBack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Voltar",
                            )
                        }
                    },
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                // Loading state display
                CircularProgressIndicator()
            } else if (state.selectedCoin != null) {
                val coin = state.selectedCoin

                // Theme-aware color definition
                val defineColor = if (isSystemInDarkTheme()) Color.White
                else Color.Unspecified

                // Main content column
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header section with coin icon and details
                    Icon(
                        imageVector = ImageVector.vectorResource(id = coin.iconRes),
                        contentDescription = coin.name,
                        tint = defineColor,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    Text(
                        text = coin.symbol,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = defineColor
                    )
                    Text(
                        text = coin.symbol,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                        color = defineColor
                    )
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val contentColorBorder = if (isSystemInDarkTheme()) Color.Green
                        else Color.Black

                        InfoCard(
                            title = stringResource(id = R.string.market_cap),
                            formattedText = coin.marketCapUsd.formatted,
                            icon = ImageVector.vectorResource(R.drawable.stock),
                            contentColor = contentColorBorder
                        )
                        InfoCard(
                            title = stringResource(id = R.string.price),
                            formattedText = coin.priceUsd.formatted,
                            icon = ImageVector.vectorResource(R.drawable.dollar),
                            contentColor = contentColorBorder
                        )
                        val absoluteChangeFormatted =
                            (coin.priceUsd.value * (coin.changePercent24Hr.value / 100))
                                .toDisplayableNumber()
                        val isPositive = coin.changePercent24Hr.value > 0.0

                        val contentColor = if (isPositive) Color.Green
                        else Color.Red

                        InfoCard(
                            title = stringResource(id = R.string.change_last_24h),
                            formattedText = absoluteChangeFormatted.formatted,
                            icon = if (isPositive) {
                                ImageVector.vectorResource(id = R.drawable.trending)
                            } else {
                                ImageVector.vectorResource(id = R.drawable.trending_down)
                            },
                            contentColor = contentColor
                        )
                    }
                    // Price chart section with adaptive visibility
                    AnimatedVisibility(
                        visible = coin.coinPriceHistory.isNotEmpty()
                    ) {
                        // Chart state management
                        var selectedDataPoint by remember {
                            mutableStateOf<DataPoint?>(null)
                        }
                        var labelWidth by remember {
                            mutableFloatStateOf(0f)
                        }
                        var totalChartWidth by remember {
                            mutableFloatStateOf(0f)
                        }

                        // Calculate visible data points based on available width
                        val amountOfVisibleDataPoints = if (labelWidth > 0) {
                            ((totalChartWidth - 2.5 * labelWidth) / labelWidth).toInt()
                        } else {
                            0
                        }
                        val startIndex =
                            (coin.coinPriceHistory.lastIndex - amountOfVisibleDataPoints)
                                .coerceAtLeast(0)

                        // Interactive price chart
                        LineChart(
                            dataPoints = coin.coinPriceHistory,
                            style = ChartStyle(
                                chartLineColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = MaterialTheme.colorScheme.secondary.copy(
                                    alpha = 0.3f
                                ),
                                selectedColor = MaterialTheme.colorScheme.primary,
                                helperLinesThicknessPx = 5f,
                                axisLinesThicknessPx = 5f,
                                labelFontSize = 14.sp,
                                minYLabelSpacing = 25.dp,
                                verticalPadding = 8.dp,
                                horizontalPadding = 8.dp,
                                xAxisLabelSpacing = 8.dp
                            ),
                            visibleDataPointsIndices = startIndex..coin.coinPriceHistory.lastIndex,
                            unit = "$",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16 / 9f)
                                .onSizeChanged { totalChartWidth = it.width.toFloat() },
                            selectedDataPoint = selectedDataPoint,
                            onSelectedDataPoint = {
                                selectedDataPoint = it
                            },
                            onXLabelWidthChange = { labelWidth = it }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Preview composable for CoinDetailScreen in both light and dark themes.
 * Demonstrates the screen layout with sample data.
 */
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@PreviewLightDark()
@Composable
private fun CoinDetailScreenPrev() {
    CoinDetailScreen(
        state = CoinListState(
            isLoading = false,
            selectedCoin = previewCoin
        ),
        navigator = null,
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.background
        )
    )
}
