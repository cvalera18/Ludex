package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.data.network.FirebaseClient
import javax.inject.Inject

class SignInAnonymouslyUseCase @Inject constructor(
    private val firebaseClient: FirebaseClient
) {
    suspend operator fun invoke(): Boolean {
        return firebaseClient.signInAnonymously() != null
    }
}