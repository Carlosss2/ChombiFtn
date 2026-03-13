package com.carlos.chombi.feauteres.history.data.di

import com.carlos.chombi.feauteres.history.data.repositories.BusHistoryRepositoryImpl
import com.carlos.chombi.feauteres.history.domain.repositories.BusHistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BusHistoryModule{
    @Binds
    abstract fun bindBusHistoryRepository(
        busHistoryRepositoryImpl: BusHistoryRepositoryImpl
    ): BusHistoryRepository

}