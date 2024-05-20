package com.cvalera.ludex.data.repository

import com.cvalera.ludex.data.datasource.local.LocalDataSource
import com.cvalera.ludex.data.datasource.remote.GameRemoteDataSource
import com.cvalera.ludex.data.network.UserService
import com.cvalera.ludex.data.network.UserService.Companion.GAMES_NODE
import com.cvalera.ludex.data.network.UserService.Companion.USERS_NODE
import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.model.GameStatus
import com.cvalera.ludex.domain.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val remoteDataSource: GameRemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val userService: UserService
) : GameRepository {
    private var lastQuery: String = ""
    private val _remoteGames = MutableStateFlow<List<Game>>(emptyList())
    override val allGames = combine(
        _remoteGames,
        localDataSource.allGames,
        userService.userGames
    ) { remote, local, firebase ->
        mergeLists(remote, local, firebase)
    }

    override suspend fun getGames() {
        val remoteGames = remoteDataSource.fetchGames()
        val userGames = userService.userGames.value
        val combinedGames = mergeLists(remoteGames, localDataSource.getAllUserGames(), userGames)

        _remoteGames.getAndUpdate { currentList ->
            (currentList + combinedGames).distinctBy { it.id }
        }

    }

    override suspend fun searchGames(query: String) {
        if (query != lastQuery) {
            remoteDataSource.resetPagination()
        }
        val queryWords = query.lowercase().split(" ").filter { it.isNotBlank() }
        val fetchedGames = remoteDataSource.searchGames(query)
        _remoteGames.getAndUpdate { currentList ->
            (currentList + fetchedGames).distinctBy { it.id }.filter { game ->
                queryWords.all { word ->
                    game.titulo.lowercase().contains(word)
                }
            }
        }
        lastQuery = query
    }

    override suspend fun syncLocalWithFirebase() {
        val userGames = userService.getUserGamesFromFirebase()
        userService.syncLocalDatabase(userGames)
    }

    private fun mergeLists(remoteGames: List<Game>, localGames: List<Game>, firebaseGames: List<Game>): List<Game> {
        val firebaseGamesMap = firebaseGames.associateBy { it.id }
        val localGamesMap = localGames.associateBy { it.id }

        return remoteGames.map { remoteGame ->
            val firebaseGame = firebaseGamesMap[remoteGame.id]
            val localGame = localGamesMap[remoteGame.id]

            when {
                firebaseGame != null -> {
                    // Prioriza los valores de Firebase
                    remoteGame.copy(
                        fav = firebaseGame.fav,
                        status = firebaseGame.status
                    )
                }
                localGame != null -> {
                    // Si no hay valores en Firebase, usa los locales
                    remoteGame.copy(
                        fav = localGame.fav,
                        status = localGame.status
                    )
                }
                else -> {
                    remoteGame
                }
            }
        }
    }

    override suspend fun getFavoriteGames(): List<Game> {
        return localDataSource.getFavoriteGames()
    }

    override suspend fun addFavoriteGame(game: Game) {
        localDataSource.addFavoriteGame(game)
        // Save the game to Firebase
//        userService.onFavUserGame(game)
        userService.saveUserGame(game.copy(fav = true))
    }

    override suspend fun unFavGame(game: Game) {
        localDataSource.unFavGame(game.id)
        // Remove the game from Firebase
        userService.saveUserGame(game.copy(fav = false))
    }
    override suspend fun updateGameStatus(game: Game, status: GameStatus) {
        localDataSource.updateGameStatus(game, status)
        userService.saveUserGame(game.copy(status = status))
    }
    override suspend fun getFavGamesByStatus(status: GameStatus): List<Game> {
        val filteredGames = getFavoriteGames()
            .filter { game ->
                status == game.status
            }
        return filteredGames
    }
    override suspend fun getListedGames(excludedStatus: GameStatus): List<Game> {
        return localDataSource.getListedGames(excludedStatus)
    }
    override fun loadMoreGames() {
        remoteDataSource.nextPage()
    }
}