package com.carlos.chombi.feauteres.authentication.data.repositories

import com.carlos.chombi.core.network.ChombiApi
import com.carlos.chombi.feauteres.authentication.data.datasources.remote.mapper.toDomain
import com.carlos.chombi.feauteres.authentication.data.datasources.remote.mapper.toRegisterDto
import com.carlos.chombi.feauteres.authentication.data.datasources.remote.model.LoginRequestDto
import com.carlos.chombi.feauteres.authentication.domain.entities.User
import com.carlos.chombi.feauteres.authentication.domain.repositories.AuthRepository
import org.json.JSONObject

class AuthRepositoryImpl(
    private val api: ChombiApi
) : AuthRepository {

    override suspend fun registerUser(user: User): User {
        // 1. Convertimos el objeto de dominio a DTO
        val userDtoToSend = user.toRegisterDto()

        // 2. Realizamos la llamada a la API envolviéndola en un objeto Response
        // Esto evita que Retrofit lance una excepción automática en errores 4xx o 5xx
        val response = api.registerUser(userDtoToSend)

        // 3. Verificamos si la respuesta fue exitosa (Códigos 200 al 299)
        if (response.isSuccessful) {
            // Si el servidor devuelve el usuario en 'data', lo mapeamos.
            // Si 'data' es nulo porque solo mandó un "message", devolvemos el objeto 'user' local.
            return response.body()?.data?.toDomain() ?: user
        } else {
            // 4. Manejo de Errores (400, 404, 500, etc.)
            // Extraemos el cuerpo del error que viene del servidor
            val errorBodyString = response.errorBody()?.string()

            val errorMessage = try {
                // Intentamos parsear el JSON de error para sacar el campo "message"
                val jsonObject = JSONObject(errorBodyString ?: "")
                jsonObject.optString("message", "Error desconocido en el servidor")
            } catch (e: Exception) {
                // Si no es un JSON o no tiene mensaje, enviamos un error genérico según el código
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
        // Preparamos el DTO de login
        val loginDto = LoginRequestDto(email, password)

        // En el login solemos esperar que la respuesta SIEMPRE traiga datos (User + Token)
        // Si aquí también recibes 500 a veces, podrías aplicar la misma lógica de Response que arriba
        val response = api.loginUser(loginDto)

        return response.data.toDomain()
            ?: throw Exception("Credenciales inválidas o error de datos")
    }
}