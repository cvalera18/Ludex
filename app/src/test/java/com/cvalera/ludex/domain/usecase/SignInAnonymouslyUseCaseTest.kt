package com.cvalera.ludex.domain.usecase

import com.cvalera.ludex.data.network.FirebaseClient
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SignInAnonymouslyUseCaseTest {

    private lateinit var signInAnonymouslyUseCase: SignInAnonymouslyUseCase
    private lateinit var firebaseClient: FirebaseClient

    @Before
    fun setUp() {
        firebaseClient = mockk(relaxed = true)
        signInAnonymouslyUseCase = SignInAnonymouslyUseCase(firebaseClient)
    }

    @Test
    fun `invoke should return true when signInAnonymously is successful`() = runTest {
        // Given
        val mockFirebaseUser = mockk<FirebaseUser>()
        coEvery { firebaseClient.signInAnonymously() } returns mockFirebaseUser

        // When
        val result = signInAnonymouslyUseCase.invoke()

        // Then
        assertTrue(result)
        coVerify { firebaseClient.signInAnonymously() }
    }

    @Test
    fun `invoke should return false when signInAnonymously fails`() = runTest {
        // Given
        coEvery { firebaseClient.signInAnonymously() } returns null

        // When
        val result = signInAnonymouslyUseCase.invoke()

        // Then
        assertFalse(result)
        coVerify { firebaseClient.signInAnonymously() }
    }
}