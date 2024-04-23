package com.cvalera.ludex.domain.model

data class Game(
    val id: Long,
    val titulo: String,
    val imagen: String,
    val plataforma: List<String>,
    val status: GameStatus,
    val fav: Boolean,
    val sinopsis: String,
    val dev: String?,
    val releaseDate: String?
)