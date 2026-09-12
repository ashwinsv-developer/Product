package com.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.product.data.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val sessionManager: SessionManager) : ViewModel() {

    val userEmail: StateFlow<String> = sessionManager.emailFlow
        .map { it ?: "Unknown" }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "Unknown"
        )

    val usageMinutes: StateFlow<Int> = sessionManager.usageMinutesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
        }
    }
}
