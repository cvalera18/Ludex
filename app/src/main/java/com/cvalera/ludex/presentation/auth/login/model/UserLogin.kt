package com.cvalera.ludex.presentation.auth.login.model

data class UserLogin(
    val email: String = "",
    val password: String = "",
    val showErrorDialog: Boolean = false
)
