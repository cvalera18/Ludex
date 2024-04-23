package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.model.GameStatus
import com.cvalera.ludex.domain.repository.GameRepository
import javax.inject.Inject

class GetListedGamesUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(excludedStatus: GameStatus): List<Game> {
        return gameRepository.getListedGames(excludedStatus)
    }
}