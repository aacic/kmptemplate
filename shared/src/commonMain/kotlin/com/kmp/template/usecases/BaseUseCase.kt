package com.kmp.template.usecases

import co.touchlab.kermit.Logger
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.Result.Companion.failure

val logger = Logger.withTag("BaseUseCase")
abstract class BaseUseCase {
    protected fun <T> safeApiCallFlow(function: suspend () -> T): Flow<Result<T>> {
        return flow {
            try {
                val result = function()
                emit(Result.success(result))
            } catch (e: IOException) {
                logger.e { "Error: $e" }
                // TODO: Implement retries
                emit(failure(e))
            } catch (e: Exception) {
                logger.e { "Error: $e" }
                emit(failure(e))
            }
        }
    }
}
