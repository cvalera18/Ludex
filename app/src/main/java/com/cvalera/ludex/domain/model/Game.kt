package com.cvalera.ludex.domain.model

data class Game(
    val id: Long = 0L,
    val titulo: String = "",
    val imagen: String = "",
    val plataforma: List<String> = emptyList(),
    val status: GameStatus = GameStatus.SIN_CLASIFICAR,
    val fav: Boolean = false,
    val sinopsis: String = "",
    val dev: String? = null,
    val releaseDate: String? = null
) {
    // Constructor sin argumentos
    constructor() : this(0L, "", "", emptyList(), GameStatus.SIN_CLASIFICAR, false, "", null, null)
}