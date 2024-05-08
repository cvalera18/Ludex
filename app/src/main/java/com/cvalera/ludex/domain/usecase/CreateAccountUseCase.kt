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
        // Intenta crear una cuenta de autenticación
        val accountCreated = authenticationService.createAccount(userSignIn.email, userSignIn.password) != null

        // Si la cuenta se crea exitosamente, intenta crear el registro en la base de datos
        return if (accountCreated) {
            userService.createUser(userSignIn)
        } else {
            false
        }
    }
}
