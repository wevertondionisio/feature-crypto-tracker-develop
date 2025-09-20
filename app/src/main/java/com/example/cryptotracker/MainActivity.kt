package com.example.cryptotracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.cryptotracker.core.navigation.AdaptiveCoinListDetailPane
import com.example.cryptotracker.ui.theme.CryptoTrackerTheme

/**
 * Main entry point of the CryptoTracker application.
 *
 * Responsibilities:
 * - Sets up the Material3 theme
 * - Enables edge-to-edge display
 * - Configures the adaptive layout scaffold
 * - Initializes the main navigation structure
 */
class MainActivity : ComponentActivity() {
    /**
     * Initializes the activity and sets up the Compose UI hierarchy.
     * Configures edge-to-edge display and applies the app theme.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AdaptiveCoinListDetailPane(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
