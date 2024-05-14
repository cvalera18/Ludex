package com.cvalera.ludex.data.response

sealed class LoginResult {
    data class Success(val verified: Boolean, val email: String) : LoginResult()
    data class Error(val message: String) : LoginResult()
}

