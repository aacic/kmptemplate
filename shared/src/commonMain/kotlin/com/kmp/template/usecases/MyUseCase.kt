package com.kmp.template.usecases

import com.kmp.template.Config
import com.kmp.template.domain.MyDomainObject
import com.kmp.template.dto.MyDto
import com.kmp.template.mapper.ResponseMapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class MyUseCase(private val httpClient: HttpClient) {

     suspend fun invoke(): Flow<Result<MyDomainObject>> {
        return flow {
            try {
                val responseDto: MyDto = httpClient.get("${Config.backendUrl}/myroute").body()
                val response = ResponseMapper.map(responseDto)

                emit(Result.success(response))
            } catch (e: IOException) {
                // Handle IOException, typically network error like server down
//                TODO Log Exception
                emit(Result.failure(e))
            } catch (e: Exception) {
//                TODO Log Exception
                // Handle other exceptions
                emit(Result.failure(e))
            }
        }
    }
}