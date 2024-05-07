package com.cvalera.ludex.di

import com.cvalera.ludex.data.datasource.local.LocalDataSource
import com.cvalera.ludex.data.datasource.remote.GameRemoteDataSource
import com.cvalera.ludex.data.repository.AuthRepository
import com.cvalera.ludex.data.repository.GameRepositoryImpl
import com.cvalera.ludex.domain.repository.GameRepository
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

//    @Singleton
//    @Provides
//    fun provideAuthRepository(): AuthRepository = AuthRepository()
}
