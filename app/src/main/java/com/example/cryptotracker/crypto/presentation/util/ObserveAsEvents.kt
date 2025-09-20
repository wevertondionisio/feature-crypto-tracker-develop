package com.example.cryptotracker.crypto.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * A lifecycle-aware composable that observes a Flow as one-time events.
 *
 * This utility helps handle events that should only be processed once,
 * such as navigation events, error messages, or other one-shot operations.
 * Events are only collected when the lifecycle is in STARTED state or above.
 *
 * @param T The type of events being observed
 * @param events The Flow of events to observe
 * @param key1 Optional key for restarting the effect
 * @param key2 Optional secondary key for restarting the effect
 * @param onEvent Callback invoked for each event
 */
@Composable
fun <T> ObserveAsEvents(
    events: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle, key1, key2) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                events.collect(onEvent)
            }
        }
    }
}