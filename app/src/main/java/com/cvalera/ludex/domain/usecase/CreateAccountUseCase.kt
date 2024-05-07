package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.data.network.AuthenticationService
import com.cvalera.ludex.data.network.UserService
import com.cvalera.ludex.presentation.auth.signin.model.UserSignIn
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService
) {

    suspend operator fun invoke(userSignIn: UserSignIn): Boolean {
        val accountCreated =
            authenticationService.createAccount(userSignIn.email, userSignIn.password) != null
        return if (accountCreated) {
            userService.createUserTable(userSignIn)
        } else {
            false
        }
    }
}