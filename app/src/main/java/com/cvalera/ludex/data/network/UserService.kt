package com.cvalera.ludex.data.network

import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.presentation.auth.signin.model.UserSignIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserService @Inject constructor(private val firebase: FirebaseClient) {

    companion object {
        const val USERS_NODE = "users"
        const val GAMES_NODE = "games"
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

    suspend fun createGoogleUser(email: String, displayName: String?): Boolean = runCatching {
        val user = hashMapOf(
            "email" to email,
            "displayName" to (displayName ?: "Unknown")
        )

        val userKey = firebase.db.child(USERS_NODE).push().key ?: throw Exception("Invalid key")

        firebase.db.child(USERS_NODE).child(userKey).setValue(user).await()

        true
    }.getOrElse {
        false
    }

    // Método para guardar juegos del usuario
    suspend fun saveUserGames(userId: String, games: List<Game>): Boolean = runCatching {
        val gamesMap = games.associateBy { it.id.toString() }
        firebase.db.child(USERS_NODE).child(userId).child(GAMES_NODE).setValue(gamesMap).await()
        true
    }.getOrElse {
        false
    }

    // Método para obtener juegos del usuario
    suspend fun getUserGames(userId: String): List<Game> = runCatching {
        val snapshot = firebase.db.child(USERS_NODE).child(userId).child(GAMES_NODE).get().await()
        snapshot.children.mapNotNull { it.getValue(Game::class.java) }
    }.getOrElse {
        emptyList()
    }
}
