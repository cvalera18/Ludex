package com.cvalera.ludex.data.network

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseClient @Inject constructor() {
    val auth: FirebaseAuth get() = FirebaseAuth.getInstance()
    val db: DatabaseReference get() = FirebaseDatabase.getInstance().reference

    // Método para iniciar sesión anónima
    suspend fun signInAnonymously(): FirebaseUser? = runCatching {
        auth.signInAnonymously().await().user
    }.getOrNull()

    // Método para enviar correo de restablecimiento de contraseña
    suspend fun sendPasswordResetEmail(email: String): Boolean = runCatching {
        auth.sendPasswordResetEmail(email).await()
        true
    }.getOrElse {
        false
    }

}