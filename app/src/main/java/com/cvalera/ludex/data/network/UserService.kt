package com.cvalera.ludex.data.network

import android.widget.Toast
import com.cvalera.ludex.core.ContextProvider
import com.cvalera.ludex.core.SessionManager
import com.cvalera.ludex.data.datasource.local.LocalDataSource
import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.model.GameStatus
import com.cvalera.ludex.presentation.auth.signin.model.UserSignIn
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserService @Inject constructor(
    private val firebase: FirebaseClient,
    private val sessionManager: SessionManager,
    private val localDataSource: LocalDataSource
) {

    companion object {
        const val USERS_NODE = "users"
        const val GAMES_NODE = "games"
    }

    private val _userGames = MutableStateFlow<List<Game>>(emptyList())
    val userGames: StateFlow<List<Game>> get() = _userGames

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val sessionValid = checkUserSession()
            if (sessionValid) {
                fetchUserGames()
            }
        }
    }

    private suspend fun checkUserSession(): Boolean {
        val currentUserId = firebase.getCurrentUserId()
        val savedUserId = sessionManager.getUserId()

        return if ((currentUserId != null) && (currentUserId != savedUserId)) {
            sessionManager.clearUserId()
            sessionManager.saveUserId(currentUserId)
            localDataSource.clearAllGames()
            true
        } else if (currentUserId != null) {
            true
        } else {
            false
        }
    }

    private fun fetchUserGames() {
        val userId = firebase.getCurrentUserId() ?: return

        firebase.db.child(USERS_NODE).child(userId).child(GAMES_NODE)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val games = snapshot.children.mapNotNull { it.getValue(Game::class.java) }
                    _userGames.value = games

                    // Sincronizar la base de datos local con los datos de Firebase
                    syncLocalDatabase(games)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Manejar el error si es necesario
                }
            })
    }
    suspend fun doesUserExist(userId: String): Boolean = runCatching {
        val snapshot = firebase.db.child(USERS_NODE).child(userId).get().await()
        snapshot.exists()
    }.getOrElse {
        false
    }

    fun syncLocalDatabase(games: List<Game>) {
        CoroutineScope(Dispatchers.IO).launch {
            // Eliminar los juegos locales que no están en Firebase
            val localGames = localDataSource.getAllUserGames()
            val gamesToRemove = localGames.filter { game ->
                !games.any { firebaseGame -> firebaseGame.id == game.id }
            }
            gamesToRemove.forEach { game ->
                localDataSource.deleteGame(game)
            }

            // Guardar los juegos de Firebase en la base de datos local
            games.forEach { game ->
                localDataSource.saveGame(game)
            }
        }
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
    suspend fun getUserGamesFromFirebase(): List<Game> = runCatching {
        val userId = firebase.getCurrentUserId() ?: throw Exception("User not logged in")
        val snapshot = firebase.db.child(USERS_NODE).child(userId).child(GAMES_NODE).get().await()
        snapshot.children.mapNotNull { it.getValue(Game::class.java) }
    }.getOrElse {
        emptyList()
    }
    suspend fun saveUserGame(game: Game): Boolean = runCatching {
        val userId = firebase.getCurrentUserId() ?: throw Exception("User not logged in")
        firebase.db.child(USERS_NODE).child(userId).child(GAMES_NODE).child(game.id.toString())
            .setValue(game).await()
        true
    }.getOrElse {
        false
    }

}
