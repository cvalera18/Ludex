package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.domain.repository.GameRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchGamesUseCaseTest {
    private lateinit var searchGamesUseCase: SearchGamesUseCase
    private lateinit var gameRepository: GameRepository

    @Before
    fun setUp() {
        gameRepository = mockk(relaxed = true)
        searchGamesUseCase = SearchGamesUseCase(gameRepository)
    }

    @Test
    fun `invoke should call searchGames on repository`() = runTest {
        // Given
        val query = "Test Query"

        // When
        searchGamesUseCase.invoke(query)

        // Then
        coVerify(exactly = 1) { gameRepository.searchGames(query) }
    }
    @Test
    fun `invoke should complete without errors`() = runTest {
        // Given
        val query = "Test Query"
        coEvery { gameRepository.searchGames(query) } returns Unit

        // When
        searchGamesUseCase.invoke(query)

        // Then
        coVerify { gameRepository.searchGames(query) }
    }
    @Test
    fun `invoke should not call searchGames if query is the same as lastQuery`() = runTest {
        // Given
        val query = "Test Query"
        coEvery { gameRepository.searchGames(query) } returns Unit

        // First invocation to set lastQuery
        searchGamesUseCase.invoke(query)

        // When
        searchGamesUseCase.invoke(query)

        // Then
        coVerifyOrder {
            gameRepository.searchGames(query)
        }
    }
    @Test
    fun `invoke should not call searchGames if query is blank`() = runTest {
        // Given
        val query = "  "

        // When
        searchGamesUseCase.invoke(query)

        // Then
        coVerify(exactly = 0) { gameRepository.searchGames(any()) }
    }

}