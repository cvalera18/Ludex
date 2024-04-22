package com.cvalera.gamelista.di

import android.content.Context
import androidx.room.Room
import com.cvalera.gamelista.data.datasource.local.database.GameDatabase
import com.cvalera.gamelista.data.datasource.local.database.dao.GameDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideGameDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, GameDatabase::class.java, "game_database").build()

    @Provides
    fun provideGameDao(database: GameDatabase): GameDao {
        return database.gameDao()
    }
}
