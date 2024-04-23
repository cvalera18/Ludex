package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.repository.GameRepository
import javax.inject.Inject

class RemoveFavoriteGameUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(game: Game) = gameRepository.unFavGame(game)
}