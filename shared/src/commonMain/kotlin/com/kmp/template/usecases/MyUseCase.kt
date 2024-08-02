package com.kmp.template.usecases

import com.kmp.template.domain.MyDomainObject
import com.kmp.template.dto.MyDto
import com.kmp.template.mapper.ResponseMapper
import com.kmp.template.routes.MyRouteParams
import com.kmp.template.routes.Routes.MY_ROUTE
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow


class MyUseCase(private val httpClient: HttpClient) : BaseUseCase() {

    suspend fun invoke(myParam: String): Flow<Result<MyDomainObject>> {
        return safeApiCallFlow {
            val responseDto: MyDto = httpClient.get(MY_ROUTE.fullRoute()){
                parameter((MY_ROUTE.params<MyRouteParams>()).myParamName, myParam)
            }.body()
            val response = ResponseMapper.map(responseDto)
            response
        }
    }
}