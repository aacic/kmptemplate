package com.kmp.template.usecases

import com.kmp.template.Config
import com.kmp.template.domain.MyDomainObject
import com.kmp.template.dto.MyDto
import com.kmp.template.mapper.ResponseMapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow


class MyUseCase(private val httpClient: HttpClient) : BaseUseCase() {

    suspend fun invoke(): Flow<Result<MyDomainObject>> {
        return safeApiCallFlow {
            val responseDto: MyDto = httpClient.get("${Config.backendUrl}/myroute").body()
            val response = ResponseMapper.map(responseDto)
            response
        }
    }
}