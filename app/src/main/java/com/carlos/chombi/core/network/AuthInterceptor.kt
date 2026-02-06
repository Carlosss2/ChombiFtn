package com.carlos.chombi.core.network

import android.util.Log
import com.carlos.chombi.core.session.TokenDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenDataStore: TokenDataStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val token: String? = runBlocking {
            tokenDataStore.getToken()
        }

        Log.d("NETWORK", "Request URL: ${chain.request().url}")
        Log.d("NETWORK", "Token presente: ${!token.isNullOrBlank()}")

        val request = if (!token.isNullOrBlank()) {
            chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }

        return chain.proceed(request)
    }
}
