package com.carlos.chombi.feauteres.authentication.data.repositories

import com.carlos.chombi.core.network.ChombiApi
import com.carlos.chombi.feauteres.authentication.data.datasources.remote.mapper.toDomain
import com.carlos.chombi.feauteres.authentication.data.datasources.remote.mapper.toRegisterDto
import com.carlos.chombi.feauteres.authentication.data.datasources.remote.model.LoginRequestDto
import com.carlos.chombi.feauteres.authentication.domain.entities.User
import com.carlos.chombi.feauteres.authentication.domain.repositories.AuthRepository

class AuthRepositoryImpl(
    private val api: ChombiApi
) : AuthRepository {

    override suspend fun registerUser(user: User): User {
        val userDtoToSend = user.toRegisterDto()
        val response = api.registerUser(userDtoToSend)

        // 3.Aquí deberías guardar el response.token en SharedPreferences/DataStore
        // saveToken(response.token)

        //
        return response.data.toDomain()
    }

    override suspend fun authUser(email: String, password: String): User {
        //
        val loginDto = LoginRequestDto(email, password)

        //
        val response = api.loginUser(loginDto)

        // Guardar token
        // saveToken(response.token)

        // Devolvemos el usuario convertido a dominio
        return response.data.toDomain()
    }
}