package com.cvalera.ludex.data.network

import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.model.GameStatus
import com.cvalera.ludex.presentation.auth.signin.model.UserSignIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserService @Inject constructor(val firebase: FirebaseClient) {

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

        val userId = firebase.getCurrentUserId() ?: throw Exception("User not logged in")

        firebase.db.child(USERS_NODE).child(userId).setValue(user).await()

        true
    }.getOrElse {
        false
    }

    suspend fun createGoogleUser(email: String, displayName: String?): Boolean = runCatching {
        val user = hashMapOf(
            "email" to email,
            "displayName" to (displayName ?: "Unknown")
        )

        val userId = firebase.getCurrentUserId() ?: throw Exception("User not logged in")

        firebase.db.child(USERS_NODE).child(userId).setValue(user).await()

        true
    }.getOrElse {
        false
    }

    suspend fun getUserGames(): List<Game> = runCatching {
        val userId = firebase.getCurrentUserId() ?: throw Exception("User not logged in")

        val snapshot = firebase.db.child(USERS_NODE).child(userId).child(GAMES_NODE).get().await()
        snapshot.children.mapNotNull { it.getValue(Game::class.java) }
    }.getOrElse {
        emptyList()
    }

    suspend fun onFavUserGame(game: Game): Boolean = runCatching {
        val userId = firebase.getCurrentUserId() ?: throw Exception("User not logged in")
        val modGame = game.copy(fav = !game.fav, status = game.status)
        firebase.db.child(USERS_NODE).child(userId).child(GAMES_NODE).child(modGame.id.toString())
            .setValue(modGame).await()

        true
    }.getOrElse {
        false
    }

    suspend fun unFavUserGame(game: Game): Boolean = runCatching {
        val userId = firebase.getCurrentUserId() ?: throw Exception("User not logged in")
        val modGame = game.copy(fav = !game.fav, status = game.status)
/*      Only delete from remote database if dont have an status and fav is false */
        if ((modGame.status == GameStatus.SIN_CLASIFICAR) && (!modGame.fav)) {
            firebase.db.child(USERS_NODE).child(userId).child(GAMES_NODE).child(modGame.id.toString())
                .removeValue().await()
            } else {
            firebase.db.child(USERS_NODE).child(userId).child(GAMES_NODE).child(modGame.id.toString())
                .setValue(modGame).await()
        }
        true
    }.getOrElse {
        false
    }

    suspend fun updateGameStatusUserGame(game: Game, status: GameStatus): Boolean = runCatching {
        val userId = firebase.getCurrentUserId() ?: throw Exception("User not logged in")
        val modGame = game.copy(fav = game.fav, status = status)
        /*      Only delete from remote database if dont have an status and fav is false */
        if ((modGame.status == GameStatus.SIN_CLASIFICAR) && (!modGame.fav)) {
            firebase.db.child(USERS_NODE).child(userId).child(GAMES_NODE).child(modGame.id.toString())
                .removeValue().await()
        } else {
            firebase.db.child(USERS_NODE).child(userId).child(GAMES_NODE).child(modGame.id.toString())
                .setValue(modGame).await()
        }
        true
    }.getOrElse {
        false
    }

}
