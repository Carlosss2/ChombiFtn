package com.carlos.chombi.feauteres.authentication.di

import com.carlos.chombi.feauteres.authentication.data.repositories.AuthRepositoryImpl
import com.carlos.chombi.feauteres.authentication.domain.repositories.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule{

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository
}
