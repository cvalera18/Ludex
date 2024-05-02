package com.cvalera.ludex.data.repository

import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun createUser(email: String, password: String): String {
        return firebaseAuth.createUserWithEmailAndPassword(email, password).await().user?.email
            ?: throw Exception("Failed to create user")
    }

    suspend fun signInUser(email: String, password: String): String {
        return firebaseAuth.signInWithEmailAndPassword(email, password).await().user?.email
            ?: throw Exception("Failed to sign in user")
    }
}
