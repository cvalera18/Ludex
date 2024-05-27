package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.repository.GameRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class RemoveFavoriteGameUseCaseTest {

    private lateinit var removeFavoriteGameUseCase: RemoveFavoriteGameUseCase
    private lateinit var gameRepository: GameRepository
    @Before
    fun setUp() {
        gameRepository = mockk(relaxed = true)
        removeFavoriteGameUseCase = RemoveFavoriteGameUseCase(gameRepository)
    }
    @Test
    fun `invoke should call unFavGame on repository`() = runTest {
        // Given
        val game = Game(id = 1L, titulo = "Test Game")

        // When
        removeFavoriteGameUseCase.invoke(game)

        // Then
        coVerify { gameRepository.unFavGame(game) }
    }
    @Test
    fun `invoke should complete without errors`() = runTest {
        // Given
        val game = Game(id = 1L, titulo = "Test Game")
        coEvery { gameRepository.unFavGame(game) } returns Unit

        // When
        removeFavoriteGameUseCase.invoke(game)

        // Then
        coVerify { gameRepository.unFavGame(game) }
    }
}