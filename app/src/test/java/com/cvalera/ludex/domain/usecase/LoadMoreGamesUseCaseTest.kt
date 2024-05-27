package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.domain.repository.GameRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class LoadMoreGamesUseCaseTest {
    private lateinit var loadMoreGamesUseCase: LoadMoreGamesUseCase
    private lateinit var gameRepository: GameRepository

    @Before
    fun setUp() {
        gameRepository = mockk(relaxed = true)
        loadMoreGamesUseCase = LoadMoreGamesUseCase(gameRepository)
    }

    @Test
    fun `invoke should call loadMoreGames on repository`() {
        // When
        loadMoreGamesUseCase.invoke()

        // Then
        verify { gameRepository.loadMoreGames() }
    }

    @Test
    fun `invoke should complete without errors`() {
        // When
        loadMoreGamesUseCase.invoke()

        // Then
        verify { gameRepository.loadMoreGames() }
    }
}
