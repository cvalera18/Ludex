package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.repository.GameRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetGamesUseCaseFlowTest {

    private lateinit var getGamesUseCaseFlow: GetGamesUseCaseFlow
    private lateinit var gameRepository: GameRepository

    @Before
    fun setUp() {
        gameRepository = mockk(relaxed = true)
        getGamesUseCaseFlow = GetGamesUseCaseFlow(gameRepository)
    }
    @Test
    fun `invoke should return flow of games from repository`() = runTest {
        // Given
        val mockGames = listOf(Game(id = 1, titulo = "Test Game"))
        val flowOfGames: Flow<List<Game>> = flowOf(mockGames)
        every { gameRepository.allGames } returns flowOfGames

        // When
        val result = getGamesUseCaseFlow.invoke()

        // Then
        assertEquals(flowOfGames, result)
    }

    @Test
    fun `invoke should call allGames on repository`() = runTest {

        // When
        getGamesUseCaseFlow.invoke()

        // Then
        coVerify { gameRepository.allGames }
    }

    @Test
    fun `invoke should emit values correctly`() = runTest {
        // Given
        val mockGames1 = listOf(Game(id = 1, titulo = "Test Game 1"))
        val mockGames2 = listOf(Game(id = 2, titulo = "Test Game 2"))
        val flowOfGames: Flow<List<Game>> = flowOf(mockGames1, mockGames2)
        every { gameRepository.allGames } returns flowOfGames

        // When
        val result = getGamesUseCaseFlow.invoke()

        // Then
        val emittedValues = result.toList()
        assertEquals(listOf(mockGames1, mockGames2), emittedValues)
    }
}