package com.cvalera.gamelista.domain.usecase

import com.cvalera.gamelista.domain.repository.GameRepository
import javax.inject.Inject

class SearchGamesUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(query: String) {
        gameRepository.searchGames(query)
    }
}
