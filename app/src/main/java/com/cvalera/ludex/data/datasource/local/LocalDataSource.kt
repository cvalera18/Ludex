package com.cvalera.ludex.data.datasource.local

import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.model.GameStatus
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    val allGames: Flow<List<Game>>
    suspend fun addFavoriteGame(game: Game)
    suspend fun unFavGame(gameId: Long)
    suspend fun updateGameStatus(game: Game, status: GameStatus)
    suspend fun getFavoriteGames(): List<Game>
    suspend fun getListedGames(excludedStatus: GameStatus): List<Game>
    suspend fun getAllUserGames(): List<Game>
    suspend fun saveGames(games: List<Game>)
    suspend fun saveGame(game: Game)
    suspend fun deleteGame(game: Game)
    suspend fun clearAllGames()
}