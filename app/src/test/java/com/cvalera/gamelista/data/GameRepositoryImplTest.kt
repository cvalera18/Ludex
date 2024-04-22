package com.cvalera.gamelista.data

import android.content.Context
import android.content.SharedPreferences
import com.cvalera.gamelista.data.repository.GameRepositoryImpl
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import org.junit.Before

class GameRepositoryImplTest {

    @RelaxedMockK
    private lateinit var repository: GameRepositoryImpl
    private val sharedPreferences: SharedPreferences = mockk(relaxed = true)
    private val editor: SharedPreferences.Editor = mockk(relaxed = true)

    @Before
    fun setUp() {
        val context = mockk<Context>(relaxed = true)

    }

}