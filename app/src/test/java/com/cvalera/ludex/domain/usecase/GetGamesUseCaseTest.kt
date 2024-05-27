package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.domain.repository.GameRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetGamesUseCaseTest {
    private lateinit var getGamesUseCase: GetGamesUseCase
    private lateinit var gameRepository: GameRepository

    @Before
    fun setup() {
        gameRepository = mockk(relaxed = true)
        getGamesUseCase = GetGamesUseCase(gameRepository)
    }

    @Test
    fun `invoke should call getGames on repository`() = runTest {
        // When
        getGamesUseCase.invoke()

        // Then
        coVerify(exactly = 1) { gameRepository.getGames() }
    }
}