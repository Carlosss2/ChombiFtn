package com.carlos.chombi.feauteres.busManagement.data.di

import com.carlos.chombi.core.network.ChombiApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChombiNetworkModule {

    @Provides
    @Singleton
    fun chombiApi(retrofit: Retrofit): ChombiApi{
        return retrofit.create(ChombiApi::class.java)
    }
}