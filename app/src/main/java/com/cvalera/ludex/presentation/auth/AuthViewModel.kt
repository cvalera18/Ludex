package com.cvalera.ludex.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvalera.ludex.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    fun createUser(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val userData = authRepository.createUser(email, password)
                onSuccess(userData)
            } catch (e: Exception) {
                onError()
            }
        }
    }

    fun signInUser(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val userData = authRepository.signInUser(email, password)
                onSuccess(userData)
            } catch (e: Exception) {
                onError()
            }
        }
    }
}
