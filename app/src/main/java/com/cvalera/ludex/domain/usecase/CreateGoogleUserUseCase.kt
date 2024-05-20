package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.data.network.AuthenticationService
import com.cvalera.ludex.data.network.UserService
import com.cvalera.ludex.data.response.LoginResult
import javax.inject.Inject

class CreateGoogleUserUseCase @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService
) {

    suspend operator fun invoke(idToken: String): Boolean {
        val result = authenticationService.loginWithGoogle(idToken)
        if (result is LoginResult.Success && result.verified) {
            // Save the user in Firebase Realtime Database
            val user = authenticationService.getCurrentUser()
            return user?.let {
                val userExists = userService.doesUserExist(it.uid)
                if (!userExists) {
                    userService.createGoogleUser(it.email ?: "", it.displayName)
                } else {
                    true // User already exists, login successful
                }
            } ?: false
        }
        return false
    }
}
