package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.domain.repository.GameRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SyncLocalWithFirebaseUseCaseTest {

    private lateinit var syncLocalWithFirebaseUseCase: SyncLocalWithFirebaseUseCase
    private lateinit var gameRepository: GameRepository
    private val testDispatcher = UnconfinedTestDispatcher()
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        gameRepository = mockk(relaxed = true)
        syncLocalWithFirebaseUseCase = SyncLocalWithFirebaseUseCase(gameRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke should call syncLocalWithFirebase on repository`() = runTest {
        // Given
        coEvery { gameRepository.syncLocalWithFirebase() } returns Unit

        // When
        syncLocalWithFirebaseUseCase.invoke()

        // Then
        coVerify { gameRepository.syncLocalWithFirebase() }
    }

}