package com.cvalera.gamelista.domain.usecase

import com.cvalera.gamelista.domain.model.Game
import com.cvalera.gamelista.domain.model.GameStatus
import com.cvalera.gamelista.domain.repository.GameRepository
import javax.inject.Inject

class GetListedGamesUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(excludedStatus: GameStatus): List<Game> {
        return gameRepository.getListedGames(excludedStatus)
    }
}