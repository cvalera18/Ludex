package com.cvalera.ludex.data.datasource.remote

import com.cvalera.ludex.domain.model.Game

interface GameRemoteDataSource {
    suspend fun fetchGames(): List<Game>
    suspend fun searchGames(query: String): List<Game>
    fun nextPage()
    fun resetPagination()
}