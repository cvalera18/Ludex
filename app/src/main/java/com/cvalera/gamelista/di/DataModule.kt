package com.cvalera.gamelista.di

import com.cvalera.gamelista.data.datasource.local.LocalDataSource
import com.cvalera.gamelista.data.datasource.local.LocalDataSourceImpl
import com.cvalera.gamelista.data.datasource.local.database.dao.GameDao
import com.cvalera.gamelista.data.datasource.remote.GameRemoteDataSource
import com.cvalera.gamelista.data.datasource.remote.IGDBGameRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideGameRemoteDataSource(): GameRemoteDataSource = IGDBGameRemoteDataSource()

    @Provides
    @Singleton
    fun provideGameDataSource(
        gameDao: GameDao,
        ioDispatcher: CoroutineDispatcher
    ): LocalDataSource = LocalDataSourceImpl(gameDao, ioDispatcher)

}
