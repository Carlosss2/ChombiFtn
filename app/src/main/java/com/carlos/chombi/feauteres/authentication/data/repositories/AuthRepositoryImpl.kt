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
        // Convertimos el objeto de dominio a DTO para enviarlo
        val userDtoToSend = user.toRegisterDto()

        // Llamada a la API
        val response = api.registerUser(userDtoToSend)

        // TODO: Guardar token si es necesario
        // saveToken(response.token)

        /**
         * response.data.toDomain() devuelve "User?".
         * El operador ?: lanza una excepción si el resultado es null,
         * cumpliendo con el tipo de retorno "User" (no nulo).
         */
        return response.data.toDomain()
            ?: throw Exception("La respuesta del servidor está vacía")
    }

    override suspend fun authUser(email: String, password: String): User {
        // Preparamos el DTO de login
        val loginDto = LoginRequestDto(email, password)

        // Llamada a la API
        val response = api.loginUser(loginDto)

        // TODO: Guardar token
        // saveToken(response.token)


        return response.data.toDomain()
            ?: throw Exception("Credenciales inválidas o error de datos")
    }
}