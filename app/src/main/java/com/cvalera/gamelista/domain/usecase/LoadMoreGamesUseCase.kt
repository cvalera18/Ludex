package com.cvalera.gamelista.domain.usecase

import com.cvalera.gamelista.domain.repository.GameRepository
import javax.inject.Inject

class LoadMoreGamesUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    operator fun invoke() {
        gameRepository.loadMoreGames()
    }
}