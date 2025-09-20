package com.example.cryptotracker.core.data.networking

import com.example.cryptotracker.BuildConfig

/**
 * Constructs a complete API URL with proper authentication.
 *
 * This utility function ensures that all API requests include:
 * - The correct base URL
 * - API key authentication
 * - Proper URL formatting and path handling
 *
 * Cases handled:
 * 1. Full URLs that already contain the base URL
 * 2. Paths starting with "/" (leading slash removed)
 * 3. Relative paths
 *
 * @param url The URL or path to construct
 * @return Complete API URL with authentication
 */
fun constructUrl(url: String): String {
    val apikey = "?apiKey=c08f74ff2cd246e121366d35bc8deeacc13736bf738738a033481b7d9d942376"

    return when {
        url.contains(BuildConfig.BASE_URL) -> url + apikey
        url.startsWith("/") -> BuildConfig.BASE_URL + url.drop(1) + apikey
        else -> BuildConfig.BASE_URL + url + apikey
    }
}