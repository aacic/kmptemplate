package com.kmp.template.usecases

import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.Result.Companion.failure

abstract class BaseUseCase {
    protected fun <T> safeApiCallFlow(function: suspend () -> T): Flow<Result<T>> {
        return flow {
            try {
                val result = function()
                emit(Result.success(result))
            } catch (e: IOException) {
                // TODO: Log Exception, handle specific IOException (network errors, etc.)
                // TODO: Implement retries
                emit(failure(e))
            } catch (e: Exception) {
                // TODO: Log Exception, handle other types of exceptions
                emit(failure(e))
            }
        }
    }
}
