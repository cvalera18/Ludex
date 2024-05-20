package com.cvalera.ludex.data.datasource.local

import com.cvalera.ludex.data.datasource.local.database.dao.GameDao
import com.cvalera.ludex.data.datasource.local.database.dao.toDomainModel
import com.cvalera.ludex.data.datasource.local.database.dao.toEntityModel
import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.model.GameStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val gameDao: GameDao,
    private val ioDispatcher: CoroutineDispatcher
) : LocalDataSource {


    override val allGames = gameDao.observeAllGames()
        .map {
            it.map { gameEntity ->
                gameEntity.toDomainModel()
            }
        }

    override suspend fun addFavoriteGame(game: Game) = withContext(ioDispatcher) {
        val entity = game.copy(fav = !game.fav, status = game.status).toEntityModel()
        gameDao.insert(entity)
    }

    override suspend fun unFavGame(gameId: Long) = withContext(ioDispatcher) {
        gameDao.deleteById(gameId)
    }

    override suspend fun updateGameStatus(game: Game, status: GameStatus) =
        withContext(ioDispatcher) {
            val entity = game.copy(status = status, fav = game.fav).toEntityModel()
            gameDao.insert(entity)
        }

    override suspend fun getFavoriteGames(): List<Game> = withContext(ioDispatcher) {
        gameDao.getFavoriteGames().map { it.toDomainModel() }
    }

    override suspend fun getListedGames(excludedStatus: GameStatus): List<Game> =
        withContext(ioDispatcher) {
            gameDao.getListedGamesNotEqual(excludedStatus.name).map { it.toDomainModel() }
        }

    override suspend fun getAllUserGames(): List<Game> = withContext(ioDispatcher) {
        gameDao.getAllUserGames().map { it.toDomainModel() }
    }

    override suspend fun saveGames(games: List<Game>) = withContext(ioDispatcher) {
        val entities = games.map { it.toEntityModel() }
        gameDao.insertAll(entities)
    }

    override suspend fun saveGame(game: Game) = withContext(ioDispatcher) {
        gameDao.insert(game.toEntityModel())
    }

    override suspend fun deleteGame(game: Game) = withContext(ioDispatcher) {
        gameDao.delete(game.toEntityModel())
    }
    override suspend fun clearAllGames() = withContext(ioDispatcher) {
        gameDao.clearAll()
    }

}