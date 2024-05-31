package com.cvalera.ludex.domain.model

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class Game(
    @PropertyName("id") val id: Long = 0L,
    @PropertyName("titulo") val titulo: String = "",
    @PropertyName("imagen") val imagen: String = "",
    @PropertyName("plataforma") val plataforma: List<String> = emptyList(),
    @PropertyName("status") val status: GameStatus = GameStatus.SIN_CLASIFICAR,
    @PropertyName("fav") val fav: Boolean = false,
    @PropertyName("sinopsis") val sinopsis: String = "",
    @PropertyName("dev") val dev: String? = null,
    @PropertyName("releaseDate") val releaseDate: String? = null
) {
    constructor() : this(0L, "", "", emptyList(), GameStatus.SIN_CLASIFICAR, false, "", null, null)
}
