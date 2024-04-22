package com.cvalera.gamelista.domain.usecase

import com.cvalera.gamelista.domain.model.Game
import com.cvalera.gamelista.domain.repository.GameRepository
import javax.inject.Inject

class GetFavoriteGamesUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(): List<Game> = gameRepository.getFavoriteGames()
}