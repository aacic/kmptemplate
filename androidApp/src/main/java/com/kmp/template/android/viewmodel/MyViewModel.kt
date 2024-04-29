package com.kmp.template.android.viewmodel

import androidx.lifecycle.viewModelScope
import com.kmp.template.domain.MyDomainObject
import com.kmp.template.usecases.MyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val myUseCase: MyUseCase
) : BaseViewModel() {

    private val myFlow = MutableStateFlow<MyDomainObject?>(null)

    val myDomainObject: StateFlow<MyDomainObject?> = myFlow

    init {
        viewModelScope.launch {
            myUseCase.invoke().collect { result ->
                println("result:" + result)
                if (result.isSuccess) {
                    val myDomainObject = result.getOrNull()
                    myDomainObject?.let {
                        myFlow.emit(it)
                    }
                } else {
                    postError("My error")
                }
            }
        }
    }
}
