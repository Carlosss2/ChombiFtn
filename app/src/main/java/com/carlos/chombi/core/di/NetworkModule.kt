package com.carlos.chombi.core.di


import android.content.Context
import com.carlos.chombi.BuildConfig
import com.carlos.chombi.core.network.AuthInterceptor
import com.carlos.chombi.core.network.ChombiApi
import com.carlos.chombi.core.session.SessionManager
import com.carlos.chombi.core.session.TokenDataStore

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideTokenDataStore(@ApplicationContext context: Context): TokenDataStore {
        return TokenDataStore(context)
    }

    @Provides
    @Singleton
    fun provideSessionManager(tokenDataStore: TokenDataStore): SessionManager {
        return SessionManager(tokenDataStore)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSharedUpApi(retrofit: Retrofit): ChombiApi {
        return retrofit.create(ChombiApi::class.java)
    }
}