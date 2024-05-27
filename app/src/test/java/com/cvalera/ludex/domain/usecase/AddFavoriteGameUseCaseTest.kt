package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.repository.GameRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddFavoriteGameUseCaseTest {

    private lateinit var addFavoriteGameUseCase: AddFavoriteGameUseCase
    private lateinit var gameRepository: GameRepository

    @Before
    fun setUp() {
        gameRepository = mockk(relaxed = true)
        addFavoriteGameUseCase = AddFavoriteGameUseCase(gameRepository)
    }
    @Test
    fun `invoke should call addFavoriteGame on repository`() = runTest {
        // Given
        val game = Game(id = 1L, titulo = "Test Game")

        // When
        addFavoriteGameUseCase.invoke(game)

        // Then
        coVerify { gameRepository.addFavoriteGame(game) }
    }

    @Test
    fun `invoke should complete without errors`() = runTest {
        // Given
        val game = Game(id = 1L, titulo = "Test Game")
        coEvery { gameRepository.addFavoriteGame(game) } returns Unit

        // When
        addFavoriteGameUseCase.invoke(game)

        // Then
        coVerify { gameRepository.addFavoriteGame(game) }
    }
}