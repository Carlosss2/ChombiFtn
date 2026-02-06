package com.carlos.chombi.core.di

import android.content.Context
import android.util.Log
import com.carlos.chombi.BuildConfig
import com.carlos.chombi.core.network.ChombiApi
import com.carlos.chombi.core.network.AuthInterceptor
import com.carlos.chombi.core.session.SessionManager
import com.carlos.chombi.core.session.TokenDataStore
import com.carlos.chombi.feauteres.authentication.data.repositories.AuthRepositoryImpl
import com.carlos.chombi.feauteres.authentication.domain.repositories.AuthRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {

    // --- SESSION ---
    private val tokenDataStore = TokenDataStore(context)

    val sessionManager: SessionManager by lazy {
        SessionManager(tokenDataStore)
    }

    // --- NETWORK ---
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(tokenDataStore))
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // --- API ---
    val chombiApi: ChombiApi by lazy {
        retrofit.create(ChombiApi::class.java)
    }

    // --- REPOSITORY ---
    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(chombiApi, tokenDataStore)
    }
}

