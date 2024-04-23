package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.repository.GameRepository
import javax.inject.Inject

class GetFavoriteGamesUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(): List<Game> = gameRepository.getFavoriteGames()
}