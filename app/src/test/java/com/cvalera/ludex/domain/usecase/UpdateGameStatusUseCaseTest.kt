package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.model.GameStatus
import com.cvalera.ludex.domain.repository.GameRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class UpdateGameStatusUseCaseTest {

    private lateinit var updateGameStatusUseCase: UpdateGameStatusUseCase
    private lateinit var gameRepository: GameRepository
    @Before
    fun setUp() {
        gameRepository = mockk(relaxed = true)
        updateGameStatusUseCase = UpdateGameStatusUseCase(gameRepository)
    }

    @Test
    fun `invoke should call updateGameStatus on repository`() = runTest {
        // Given
        val game = Game(id = 1L, titulo = "Test Game")
        val newStatus = GameStatus.COMPLETADO

        // When
        updateGameStatusUseCase.invoke(game, newStatus)

        // Then
        coVerify { gameRepository.updateGameStatus(game, newStatus) }
    }
    @Test
    fun `invoke should complete without errors`() = runTest {
        // Given
        val game = Game(id = 1L, titulo = "Test Game")
        val newStatus = GameStatus.COMPLETADO
        coEvery { gameRepository.updateGameStatus(game, newStatus) } returns Unit

        // When
        updateGameStatusUseCase.invoke(game, newStatus)

        // Then
        coVerify { gameRepository.updateGameStatus(game, newStatus) }
    }
}