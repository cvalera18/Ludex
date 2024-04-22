package com.cvalera.gamelista.domain.repository

import com.cvalera.gamelista.domain.model.Game
import com.cvalera.gamelista.domain.model.GameStatus
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    val allGames: Flow<List<Game>>
    suspend fun getGames()
    suspend fun searchGames(query: String)
    suspend fun getFavoriteGames(): List<Game>
    suspend fun updateGameStatus(game: Game, status: GameStatus)
    suspend fun getFavGamesByStatus(status: GameStatus): List<Game>
    suspend fun getListedGames(excludedStatus: GameStatus): List<Game>
    suspend fun addFavoriteGame(game: Game)
    suspend fun unFavGame(game: Game)
    fun loadMoreGames()
}
