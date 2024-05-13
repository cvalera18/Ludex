package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.data.network.FirebaseClient
import javax.inject.Inject

class RecoverPasswordUseCase @Inject constructor(private val firebaseClient: FirebaseClient) {
    suspend operator fun invoke(email: String): Boolean {
        return firebaseClient.sendPasswordResetEmail(email)
    }
}
