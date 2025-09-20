package com.example.cryptotracker.core.data.networking

import com.example.cryptotracker.BuildConfig

fun constructUrl(url: String): String {
    val apikey = "?apiKey=c08f74ff2cd246e121366d35bc8deeacc13736bf738738a033481b7d9d942376"

    return when {
        url.contains(BuildConfig.BASE_URL) -> url + apikey
        url.startsWith("/") -> BuildConfig.BASE_URL + url.drop(1) + apikey
        else -> BuildConfig.BASE_URL + url + apikey
    }
}