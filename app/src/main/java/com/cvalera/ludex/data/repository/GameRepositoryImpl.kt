package com.cvalera.ludex.data.repository

import com.cvalera.ludex.data.datasource.local.LocalDataSource
import com.cvalera.ludex.data.datasource.remote.GameRemoteDataSource
import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.model.GameStatus
import com.cvalera.ludex.domain.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.getAndUpdate
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val remoteDataSource: GameRemoteDataSource,
    private val localDataSource: LocalDataSource
) : GameRepository {
    private var lastQuery: String = ""

    private val _remoteGames = MutableStateFlow<List<Game>>(emptyList())
    override val allGames = combine(_remoteGames, localDataSource.allGames) { remote, local ->
        mergeLists(remote, local)
    }

    override suspend fun getGames() {
        val remoteGames = remoteDataSource.fetchGames()
        _remoteGames.getAndUpdate { currentList ->
            (currentList + remoteGames).distinctBy { it.id }
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

    private fun mergeLists(remoteGames: List<Game>, localGames: List<Game>): List<Game> {
        val localGamesMap = localGames.associateBy { it.id }
        return remoteGames.map { remoteGame ->
            localGamesMap[remoteGame.id]?.let { localGame ->
                remoteGame.copy(fav = localGame.fav, status = localGame.status)
            } ?: remoteGame
        }
    }

    override suspend fun getFavoriteGames(): List<Game> {
        return localDataSource.getFavoriteGames()
    }

    override suspend fun addFavoriteGame(game: Game) {
        localDataSource.addFavoriteGame(game)
    }

    override suspend fun unFavGame(game: Game) {
        localDataSource.unFavGame(game.id)
    }

    override suspend fun updateGameStatus(game: Game, status: GameStatus) {
        localDataSource.updateGameStatus(game, status)
    }

    // TODO FavViewModel needs to call this function
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