package com.carlos.chombi.core.session

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "session")

class TokenDataStore(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
        Log.d("DATASTORE", "Token guardado")
    }

    suspend fun getToken(): String? {
        val token = context.dataStore.data.first()[TOKEN_KEY]
        Log.d("DATASTORE", "Token leído: ${token?.take(10)}...")
        return token
    }

    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
        Log.d("DATASTORE", "Token eliminado")
    }
}
