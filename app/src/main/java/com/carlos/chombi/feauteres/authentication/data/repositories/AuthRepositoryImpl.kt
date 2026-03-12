package com.carlos.chombi.feauteres.authentication.data.repositories

import android.util.Log
import com.carlos.chombi.core.network.ChombiApi
import com.carlos.chombi.core.session.TokenDataStore
import com.carlos.chombi.feauteres.authentication.data.datasources.remote.mapper.toDomain
import com.carlos.chombi.feauteres.authentication.data.datasources.remote.mapper.toRegisterDto
import com.carlos.chombi.feauteres.authentication.data.datasources.remote.model.LoginRequestDto
import com.carlos.chombi.feauteres.authentication.domain.entities.User
import com.carlos.chombi.feauteres.authentication.domain.repositories.AuthRepository
import org.json.JSONObject
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ChombiApi,
    private val tokenDataStore: TokenDataStore
) : AuthRepository {

    override suspend fun registerUser(user: User): User {
        val userDtoToSend = user.toRegisterDto()
        val response = api.registerUser(userDtoToSend)

        if (response.isSuccessful) {
            val body = response.body()
                ?: throw Exception("Respuesta del servidor vacía")

            // Guardar token si viene
            body.token?.let { tokenDataStore.saveToken(it) }

            // <-- CAMBIO AQUÍ: Llamamos toDomain directamente sobre body
            return body.toDomain() ?: user

        } else {
            val errorBodyString = response.errorBody()?.string()

            val errorMessage = try {
                val jsonObject = JSONObject(errorBodyString ?: "")
                jsonObject.optString("message", "Error desconocido en el servidor")
            } catch (e: Exception) {
                when (response.code()) {
                    500 -> "Error interno del servidor (500). Inténtalo más tarde."
                    401 -> "No autorizado. Verifica tus credenciales."
                    else -> "Error inesperado: ${response.code()}"
                }
            }

            throw Exception(errorMessage)
        }
    }

    override suspend fun authUser(email: String, password: String): User {
        val response = api.loginUser(
            LoginRequestDto(email, password)
        )

        // Validamos que el token exista
        val token = response.token ?: throw Exception("El servidor no devolvió un token")

        // Guardar token
        tokenDataStore.saveToken(token)
        Log.d("DEBUG_TOKEN", token)

        // <-- CAMBIO AQUÍ: Convertimos directamente response (que es AuthResponse) a dominio
        return response.toDomain()
            ?: throw Exception("No se pudieron procesar los datos del usuario")
    }
}