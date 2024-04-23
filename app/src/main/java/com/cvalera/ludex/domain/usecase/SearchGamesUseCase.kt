package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.domain.repository.GameRepository
import javax.inject.Inject

class SearchGamesUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(query: String) {
        gameRepository.searchGames(query)
    }
}
