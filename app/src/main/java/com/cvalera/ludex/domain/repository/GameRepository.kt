package com.cvalera.ludex.domain.repository

import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.model.GameStatus
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    val allGames: Flow<List<Game>>
//    suspend fun syncLocalWithFirebase()
    suspend fun getGames()
    suspend fun searchGames(query: String)
    suspend fun getFavoriteGames(): List<Game>
    suspend fun updateGameStatus(game: Game, status: GameStatus)
    suspend fun getFavGamesByStatus(status: GameStatus): List<Game>
    suspend fun getListedGames(excludedStatus: GameStatus): List<Game>
    suspend fun addFavoriteGame(game: Game)
    suspend fun unFavGame(game: Game)
    suspend fun syncLocalWithFirebase()
    fun loadMoreGames()
}
