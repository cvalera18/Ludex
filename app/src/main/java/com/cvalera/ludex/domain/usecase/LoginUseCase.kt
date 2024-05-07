package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.data.network.AuthenticationService
import com.cvalera.ludex.data.response.LoginResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authenticationService: AuthenticationService) {

    suspend operator fun invoke(email: String, password: String): LoginResult =
        authenticationService.login(email, password)
}