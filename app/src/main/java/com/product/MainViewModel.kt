package com.product

import androidx.lifecycle.ViewModel
import com.product.data.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val sessionManager: SessionManager) : ViewModel() {

    fun getUserEmail(): String {
        return sessionManager.getEmail() ?: "Unknown"
    }
    fun logout() {
        sessionManager.clearSession()
    }

}