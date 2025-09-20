package com.example.cryptotracker.core.data.networking

import android.content.Context
import com.example.cryptotracker.R
import com.example.cryptotracker.core.domain.util.NetworkError

/**
 * Extension function that converts NetworkError enum values to user-friendly error messages.
 *
 * @param context Android Context used to retrieve localized string resources
 * @return A localized string message describing the network error
 */
fun NetworkError.toErrorString(context: Context): String  {
    val resId = when(this) {
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        NetworkError.NO_INTERNET -> R.string.error_no_internet
        NetworkError.SERVER_ERROR -> R.string.error_unknown
        NetworkError.SERIALIZATION -> R.string.error_serialization
        NetworkError.UNKNOWN -> R.string.error_unknown
    }
    return context.getString(resId)
}