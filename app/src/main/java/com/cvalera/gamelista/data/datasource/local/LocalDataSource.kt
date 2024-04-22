package com.cvalera.gamelista.data.datasource.local

import com.cvalera.gamelista.domain.model.Game
import com.cvalera.gamelista.domain.model.GameStatus
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    val allGames: Flow<List<Game>>
    suspend fun addFavoriteGame(game: Game)
    suspend fun unFavGame(gameId: Long)
    suspend fun updateGameStatus(game: Game, status: GameStatus)
    suspend fun getFavoriteGames(): List<Game>
    suspend fun getListedGames(excludedStatus: GameStatus): List<Game>
    suspend fun getAllUserGames(): List<Game>
}