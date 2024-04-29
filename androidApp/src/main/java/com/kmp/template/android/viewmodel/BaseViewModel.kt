package com.kmp.template.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val errorFlow = MutableStateFlow<String?>(null)

    val error: StateFlow<String?> = errorFlow

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
