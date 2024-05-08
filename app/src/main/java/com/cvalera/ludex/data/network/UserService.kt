package com.cvalera.ludex.data.network

import com.cvalera.ludex.presentation.auth.signin.model.UserSignIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserService @Inject constructor(private val firebase: FirebaseClient) {

    companion object {
        const val USERS_NODE = "users"
    }

    suspend fun createUser(userSignIn: UserSignIn): Boolean = runCatching {
        val user = hashMapOf(
            "email" to userSignIn.email,
            "nickname" to userSignIn.nickName,
            "realname" to userSignIn.realName
        )

        // Obtiene una nueva clave para un nuevo usuario
        val userKey = firebase.db.child(USERS_NODE).push().key ?: throw Exception("Invalid key")

        // Establece los valores del usuario en la nueva clave
        firebase.db.child(USERS_NODE).child(userKey).setValue(user).await()

        true
    }.getOrElse {
        false
    }
}
