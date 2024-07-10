package com.kmp.template.android.di

import com.kmp.template.usecases.MyUseCase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {

        return HttpClient {
            install(ContentNegotiation) {
                json(Json { isLenient = true; ignoreUnknownKeys = true })
            }

            install(Logging) {
                level = LogLevel.ALL // Set the logging level (ALL, HEADERS, BODY, INFO, etc.)
                logger = Logger.DEFAULT
            }

            HttpResponseValidator {
                validateResponse { response ->
                    val statusCode = response.status.value
                    if (statusCode !in 200..299) {
                        throw ResponseException(response, "Response status: $statusCode")
                    }
                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideGetPlacesUseCase(httpClient: HttpClient): MyUseCase {
        return MyUseCase(httpClient)
    }
}
