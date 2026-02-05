package com.carlos.chombi.core.di

import android.content.Context
import com.carlos.chombi.BuildConfig
import com.carlos.chombi.core.network.ChombiApi
import com.carlos.chombi.feauteres.authentication.data.repositories.AuthRepositoryImpl
import com.carlos.chombi.feauteres.authentication.domain.repositories.AuthRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val chombiApi: ChombiApi by lazy {
    retrofit.create(ChombiApi::class.java)
    }

    val AuthRepository: AuthRepository by lazy{
        AuthRepositoryImpl(chombiApi)
    }



}