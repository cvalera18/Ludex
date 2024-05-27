package com.cvalera.ludex.data.repository

import com.cvalera.ludex.data.datasource.local.LocalDataSource
import com.cvalera.ludex.data.datasource.remote.GameRemoteDataSource
import com.cvalera.ludex.data.network.UserService
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class GameRepositoryImplTest {
    private lateinit var gameRepository: GameRepositoryImpl
    private lateinit var remoteDataSource: GameRemoteDataSource
    private lateinit var localDataSource: LocalDataSource
    private lateinit var userService: UserService

    @Before
    fun setUp() {
        remoteDataSource = mockk(relaxed = true)
        localDataSource = mockk(relaxed = true)
        userService = mockk(relaxed = true)
        gameRepository = GameRepositoryImpl(remoteDataSource, localDataSource, userService)
    }

    @Test
    fun `loadMoreGames should call nextPage on remoteDataSource`() {
        // When
        gameRepository.loadMoreGames()

        // Then
        verify { remoteDataSource.nextPage() }
    }
}