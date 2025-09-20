package com.example.cryptotracker.core.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Factory object for creating and configuring Ktor HTTP clients.
 *
 * Provides centralized configuration for:
 * - Logging with custom KtorLogger
 * - JSON content negotiation
 * - Default request configuration
 * - Error handling
 */
object HttpClientFactory {
    /**
     * Creates a configured HttpClient instance.
     *
     * Configuration includes:
     * - Logging at ALL level with custom logger
     * - JSON content negotiation with lenient parsing
     * - Default request headers and content type
     * - Pretty printing for debug purposes
     *
     * @param engine The HTTP engine to use (e.g., CIO, OkHttp)
     * @return Configured HttpClient instance
     */
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(Logging) {
                level = LogLevel.ALL
                logger = KtorLogger()
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}