package com.kmp.template.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val isLoadingFlow = MutableStateFlow(false)
    private val errorFlow = MutableStateFlow<String?>(null)

    val isLoading: StateFlow<Boolean> = isLoadingFlow
    val error: StateFlow<String?> = errorFlow

    protected fun <T : Any> invokeUseCase(
        resultFlow: FlowCollector<T?>,
        errorMessage: String,
        useCaseInvocation: suspend () -> Flow<Result<T>>,
    ) {
        viewModelScope.launch {
            isLoadingFlow.value = true
            useCaseInvocation().collect { result ->
                isLoadingFlow.value = false
                if (result.isSuccess) {
                    val res = result.getOrNull()
                    res?.let {
                        resultFlow.emit(it)
                    }
                } else {
                    postError(errorMessage)
                }
            }
        }
    }

    protected fun postError(errorMessage: String) {
        viewModelScope.launch {
            errorFlow.emit(errorMessage)
        }

    }

    fun clearError() {
        viewModelScope.launch {
            errorFlow.emit(null)
        }

    }
}
