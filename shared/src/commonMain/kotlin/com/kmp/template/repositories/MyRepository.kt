package com.kmp.template.repositories

import com.kmp.template.domain.MyDomainObject
import com.kmp.template.dto.MyDto
import com.kmp.template.mapper.ResponseMapper
import com.kmp.template.routes.MyRouteParams
import com.kmp.template.routes.Routes.MY_ROUTE
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MyRepository(private val httpClient: HttpClient) {

	suspend fun getMyDomainObject(myParam: String): MyDomainObject {
		val responseDto: MyDto = httpClient.get(MY_ROUTE.fullRoute()) {
			parameter(MY_ROUTE.params<MyRouteParams>().myParamName, myParam)
		}.body()

		return ResponseMapper.map(responseDto)
	}
}