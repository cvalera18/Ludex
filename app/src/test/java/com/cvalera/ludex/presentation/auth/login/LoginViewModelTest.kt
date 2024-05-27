package com.cvalera.ludex.presentation.auth.login

import com.cvalera.ludex.data.network.AuthenticationService
import com.cvalera.ludex.data.response.LoginResult
import com.cvalera.ludex.domain.usecase.LoginUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginUseCaseTest {

    private lateinit var loginUseCase: LoginUseCase
    private lateinit var authenticationService: AuthenticationService

    @Before
    fun setUp() {
        authenticationService = mockk(relaxed = true)
        loginUseCase = LoginUseCase(authenticationService)
    }

    @Test
    fun `invoke should return success result when login is successful`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val expectedResult = LoginResult.Success(true, email)
        coEvery { authenticationService.login(email, password) } returns expectedResult

        // When
        val result = loginUseCase(email, password)

        // Then
        assertEquals(expectedResult, result)
        coVerify { authenticationService.login(email, password) }
    }

    @Test
    fun `invoke should return error result when login fails`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val expectedResult = LoginResult.Error("Login failed")
        coEvery { authenticationService.login(email, password) } returns expectedResult

        // When
        val result = loginUseCase(email, password)

        // Then
        assertEquals(expectedResult, result)
        coVerify { authenticationService.login(email, password) }
    }

    /*
    @Test
    fun `invoke should return error result when exception is thrown`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val expectedResult = LoginResult.Error("Exception message")
        coEvery { authenticationService.login(email, password) } throws Exception("Exception message")

        // When
        val result = loginUseCase(email, password)

        // Then
        assertEquals(expectedResult, result)
        coVerify { authenticationService.login(email, password) }
    }*/
}