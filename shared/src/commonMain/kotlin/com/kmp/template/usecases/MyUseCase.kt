package com.kmp.template.usecases

import com.kmp.template.domain.MyDomainObject
import com.kmp.template.repositories.MyRepository
import kotlinx.coroutines.flow.Flow


class MyUseCase(private val myRepository: MyRepository) : BaseUseCase() {

    fun invoke(myParam: String): Flow<Result<MyDomainObject>> {
        return safeApiCallFlow {
            myRepository.getMyDomainObject(myParam)
        }
    }
}