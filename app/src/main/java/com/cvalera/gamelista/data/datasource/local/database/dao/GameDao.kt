package com.cvalera.gamelista.data.datasource.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cvalera.gamelista.data.datasource.local.database.entities.GameEntity
import com.cvalera.gamelista.domain.model.Game
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(game: GameEntity)
    @Query("SELECT * FROM games_table")
    fun observeAllGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM games_table")
    fun getAllUserGames(): List<GameEntity>

    @Query("SELECT * FROM games_table WHERE fav = 1")
    fun observeModifiedGames(): Flow<List<GameEntity>>

//    @Query("DELETE FROM games_table WHERE id = :gameId")
    @Query("UPDATE games_table SET fav = NOT fav WHERE id = :gameId")
    suspend fun deleteById(gameId: Long)

    @Query("SELECT * FROM games_table WHERE fav = 1")
    suspend fun getFavoriteGames(): List<GameEntity>

    @Query("SELECT * FROM games_table WHERE id = :id")
    suspend fun getGameById(id: Long): GameEntity?

    @Query("SELECT * FROM games_table WHERE status != :excludedStatus")
    suspend fun getListedGamesNotEqual(excludedStatus: String): List<GameEntity>

}

fun Game.toEntityModel(): GameEntity {
    return GameEntity(
        id = this.id,
        titulo = this.titulo,
        imagen = this.imagen,
        plataforma = this.plataforma.joinToString(","),
        status = this.status,
        fav = this.fav,
        sinopsis = this.sinopsis,
        dev = this.dev,
        releaseDate = this.releaseDate
    )
}

fun GameEntity.toDomainModel(): Game {
    return Game(
        id = this.id,
        titulo = this.titulo,
        imagen = this.imagen,
        plataforma = this.plataforma.split(","),
        status = this.status,
        fav = this.fav,
        sinopsis = this.sinopsis,
        dev = this.dev,
        releaseDate = this.releaseDate
    )
}