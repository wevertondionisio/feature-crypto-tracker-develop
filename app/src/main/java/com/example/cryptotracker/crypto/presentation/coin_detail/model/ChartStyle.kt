package com.example.cryptotracker.crypto.presentation.coin_detail.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

/**
 * Configuration class for customizing the appearance of the LineChart.
 * Defines colors, dimensions, and styling properties for the chart components.
 */
data class ChartStyle(
    /** Color of the main chart line that shows price movement */
    val chartLineColor: Color,
    /** Color for non-selected elements like grid lines and labels */
    val unselectedColor: Color,
    /** Color for selected/highlighted elements and data points */
    val selectedColor: Color,
    /** Thickness of the background grid lines in pixels */
    val helperLinesThicknessPx: Float,
    /** Thickness of the axis lines in pixels */
    val axisLinesThicknessPx: Float,
    /** Font size for axis labels and values */
    val labelFontSize: TextUnit,
    /** Minimum spacing between Y-axis labels */
    val minYLabelSpacing: Dp,
    /** Padding at the top and bottom of the chart */
    val verticalPadding: Dp,
    /** Padding at the left and right of the chart */
    val horizontalPadding: Dp,
    /** Spacing between X-axis labels */
    val xAxisLabelSpacing: Dp
)
