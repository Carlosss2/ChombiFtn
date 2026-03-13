package com.carlos.chombi.feauteres.home.data.di

import com.carlos.chombi.feauteres.home.data.repositories.BusRepositoryImpl
import com.carlos.chombi.feauteres.home.domain.repositories.BusRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BusModule{

    @Binds
    abstract fun bindChombiRepository(
        chombiRepositoryImpl: BusRepositoryImpl
    ): BusRepository

}
