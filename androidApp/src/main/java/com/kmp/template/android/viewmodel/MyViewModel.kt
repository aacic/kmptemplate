package com.kmp.template.android.viewmodel

import com.kmp.template.domain.MyDomainObject
import com.kmp.template.usecases.MyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val myUseCase: MyUseCase
) : BaseViewModel() {

    private val myFlow = MutableStateFlow<MyDomainObject?>(null)

    val myDomainObject: StateFlow<MyDomainObject?> = myFlow

    fun invokeMyUseCase() {
        this.invokeUseCase (myFlow,"My error" ) {
            myUseCase.invoke()
        }
    }
}
