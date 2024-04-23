package com.cvalera.ludex.data.datasource.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cvalera.ludex.domain.model.GameStatus

@Entity(tableName = "games_table")
data class GameEntity(
    @PrimaryKey val id: Long,
    val titulo: String,
    val imagen: String,
    val plataforma: String,
    val status: GameStatus,
    val fav: Boolean,
    val sinopsis: String,
    val dev: String?,
    val releaseDate: String?
)
