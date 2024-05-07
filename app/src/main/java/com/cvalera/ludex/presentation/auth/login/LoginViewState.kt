package com.cvalera.ludex.presentation.auth.login

data class LoginViewState(
    val isLoading: Boolean = false,
    val isValidEmail: Boolean = true,
    val isValidPassword: Boolean = true
)