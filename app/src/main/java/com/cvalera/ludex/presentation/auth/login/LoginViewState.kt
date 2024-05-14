package com.cvalera.ludex.presentation.auth.login

import com.cvalera.ludex.presentation.auth.login.model.UserLogin

data class LoginViewState(
    val isLoading: Boolean = false,
    val isValidEmail: Boolean = true,
    val isValidPassword: Boolean = true,
    val isUserAuthenticated: Boolean = false,
    val errorMessage: String? = null,
    val authenticatedUser: UserLogin? = null
)
