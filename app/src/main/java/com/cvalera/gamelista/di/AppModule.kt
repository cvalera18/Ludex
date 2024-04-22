package com.cvalera.gamelista.di

import com.cvalera.gamelista.data.datasource.local.LocalDataSource
import com.cvalera.gamelista.data.datasource.remote.GameRemoteDataSource
import com.cvalera.gamelista.data.repository.GameRepositoryImpl
import com.cvalera.gamelista.domain.repository.GameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideRepository(
        localDataSource: LocalDataSource,
        gameRemoteDataSource: GameRemoteDataSource
    ): GameRepository = GameRepositoryImpl(gameRemoteDataSource, localDataSource)
}
