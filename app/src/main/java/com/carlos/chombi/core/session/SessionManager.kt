package com.carlos.chombi.core.session

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SessionManager(
    private val tokenDataStore: TokenDataStore
) {

    suspend fun isLoggedIn(): Boolean = withContext(Dispatchers.IO) {
        val token = tokenDataStore.getToken()
        val loggedIn = !token.isNullOrBlank()
        Log.d("SESSION", "¿Usuario logueado? $loggedIn")
        loggedIn
    }

    suspend fun logout() {
        Log.d("SESSION", "Cerrando sesión")
        tokenDataStore.clearToken()
    }
}
