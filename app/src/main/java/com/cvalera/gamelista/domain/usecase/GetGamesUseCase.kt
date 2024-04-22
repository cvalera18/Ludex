package com.cvalera.gamelista.domain.usecase

import com.cvalera.gamelista.domain.model.Game
import com.cvalera.gamelista.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGamesUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke() {
        gameRepository.getGames()
    }
}

class GetGamesUseCaseFlow @Inject constructor(
    private val gameRepository: GameRepository
) {
    fun invoke(): Flow<List<Game>> = gameRepository.allGames
}