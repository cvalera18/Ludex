package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.domain.repository.GameRepository
import javax.inject.Inject

class LoadMoreGamesUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    operator fun invoke() {
        gameRepository.loadMoreGames()
    }
}