package com.cvalera.ludex.data.network

import com.cvalera.ludex.data.response.LoginResult
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationService @Inject constructor(private val firebase: FirebaseClient) {

    val verifiedAccount: Flow<Boolean> = flow {
        while (true) {
            val verified = verifyEmailIsVerified()
            emit(verified)
            delay(1000)
        }
    }

    suspend fun login(email: String, password: String): LoginResult {
        return try {
            val result = firebase.auth.signInWithEmailAndPassword(email, password).await()
            result.user?.let {
                if (it.isEmailVerified) {
                    LoginResult.Success(it.isEmailVerified, it.email ?: "No email")
                } else {
                    LoginResult.Error("Usuario no verificado o error de autenticación.")
                }
            } ?: LoginResult.Error("Error desconocido")
        } catch (e: Exception) {
            LoginResult.Error(e.message ?: "Error desconocido")
        }
    }

    suspend fun loginWithGoogle(idToken: String): LoginResult = runCatching {
        val credential = com.google.firebase.auth.GoogleAuthProvider.getCredential(idToken, null)
        firebase.auth.signInWithCredential(credential).await()
    }.toLoginResult()

    fun getCurrentUser() = firebase.auth.currentUser
    suspend fun createAccount(email: String, password: String): AuthResult? {
        return firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun sendVerificationEmail() = runCatching {
        firebase.auth.currentUser?.sendEmailVerification()?.await() ?: false
    }.isSuccess

    private suspend fun verifyEmailIsVerified(): Boolean {
        firebase.auth.currentUser?.reload()?.await()
        return firebase.auth.currentUser?.isEmailVerified ?: false
    }

    private fun Result<AuthResult>.toLoginResult(): LoginResult {
        val authResult = getOrNull()
        val user = authResult?.user

        return when {
            authResult == null -> LoginResult.Error("Failed to authenticate.")
            user == null -> LoginResult.Error("Authentication failed, no user data.")
            else -> if (user.isEmailVerified) {
                // Asegúrate de incluir el email del usuario aquí
                LoginResult.Success(user.isEmailVerified, user.email ?: "No email")
            } else {
                LoginResult.Error("User is not verified or authentication failed.")
            }
        }
    }
}