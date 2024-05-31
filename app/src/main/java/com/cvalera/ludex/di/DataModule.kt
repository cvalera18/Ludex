package com.cvalera.ludex.di

import com.cvalera.ludex.data.datasource.local.LocalDataSource
import com.cvalera.ludex.data.datasource.local.LocalDataSourceImpl
import com.cvalera.ludex.data.datasource.local.database.dao.GameDao
import com.cvalera.ludex.data.datasource.remote.GameRemoteDataSource
import com.cvalera.ludex.data.datasource.remote.IGDBGameRemoteDataSource
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
//    fun provideGameRemoteDataSource(): GameRemoteDataSource = IGDBJsonRemoteDataSource()
    fun provideGameRemoteDataSource(): GameRemoteDataSource = IGDBGameRemoteDataSource()

    @Provides
    @Singleton
    fun provideGameDataSource(
        gameDao: GameDao,
        ioDispatcher: CoroutineDispatcher
    ): LocalDataSource = LocalDataSourceImpl(gameDao, ioDispatcher)

}
