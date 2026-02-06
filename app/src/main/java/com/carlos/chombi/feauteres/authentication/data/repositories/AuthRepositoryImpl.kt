package com.carlos.chombi.feauteres.authentication.data.repositories

import android.util.Log
import com.carlos.chombi.core.network.ChombiApi
import com.carlos.chombi.core.session.TokenDataStore
import com.carlos.chombi.feauteres.authentication.data.datasources.remote.mapper.toDomain
import com.carlos.chombi.feauteres.authentication.data.datasources.remote.mapper.toRegisterDto
import com.carlos.chombi.feauteres.authentication.data.datasources.remote.model.LoginRequestDto
import com.carlos.chombi.feauteres.authentication.domain.entities.User
import com.carlos.chombi.feauteres.authentication.domain.repositories.AuthRepository

class AuthRepositoryImpl(
    private val api: ChombiApi,
    private val tokenDataStore: TokenDataStore
) : AuthRepository {


    override suspend fun registerUser(user: User): User {

        val response = api.registerUser(user.toRegisterDto())

        tokenDataStore.saveToken(response.token)

        return response.data.toDomain()
            ?: throw Exception("Respuesta del servidor vacía")
    }

    override suspend fun authUser(email: String, password: String): User {


        val response = api.loginUser(LoginRequestDto(email, password))

        tokenDataStore.saveToken(response.token)

        return response.data.toDomain()
            ?: throw Exception("Credenciales inválidas")
        Log.d("DEBUG_TOKEN", response.token)

    }
}
